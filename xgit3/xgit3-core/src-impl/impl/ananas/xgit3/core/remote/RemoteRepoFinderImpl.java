package impl.ananas.xgit3.core.remote;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteContext;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.RemoteRepoFinder;
import ananas.xgit3.core.remote.RemoteRepoInfo;

import com.alibaba.fastjson.JSONObject;

final class RemoteRepoFinderImpl implements RemoteRepoFinder {

	private final RemoteContext _context;

	public RemoteRepoFinderImpl(RemoteContext context) {
		this._context = context;
	}

	@Override
	public RemoteRepoInfo findRepoInfo(final String uri) {

		HttpClient http = this._context.getHttpClient();
		String uriptr = uri;
		Document doc = null;
		for (; uriptr != null;) {
			MyXrdsLoader ldr = new MyXrdsLoader(http);
			if (ldr.load(uriptr)) {
				doc = ldr.getXRDS();
				break;
			} else {
				uriptr = ldr.nextURI();
			}
		}
		DocumentReader dr = new DocumentReader();
		dr.read(doc);
		MyRepoInfo info = new MyRepoInfo(_context, uri, dr);
		// ping repo
		RemoteRepo repo = _context.getRepo(info);
		JSONRequest req = repo.createJSONRequest();
		req.setTarget("getInfo", "Bucket", null);
		req.setParameter("location", uri);
		JSONObject resp = req.request();
		info.putJSONInfo(resp);

		return info;
	}

	static private class MyRepoInfo implements RemoteRepoInfo {

		private final String _ctrl_ep;
		private final RemoteContext _context;
		private final String _repo_uri;
		private final long _expires;

		public MyRepoInfo(RemoteContext context, String uri, DocumentReader dr) {
			this._context = context;
			this._repo_uri = uri;
			this._ctrl_ep = dr.getControlEP();
			this._expires = dr.getExpires();
		}

		public void putJSONInfo(JSONObject rlt) {
			// TODO Auto-generated method stub

		}

		@Override
		public RemoteContext getContext() {
			return this._context;
		}

		@Override
		public String getControlEndpointURI() {
			return this._ctrl_ep;
		}

		@Override
		public String getRepoURI() {
			return this._repo_uri;
		}

		@Override
		public long getExpires() {
			return this._expires;
		}
	}

	static class DocumentReader {

		private String _ctrl_ep;
		private long _expires;

		public void read(Document doc) {
			this.enum_nodes(doc.getDocumentElement(), 20);
		}

		public long getExpires() {
			return this._expires;
		}

		private void enum_nodes(Element element, int depthLimit) {

			if (depthLimit < 0) {
				throw new RuntimeException("too deep");
			}

			StringBuilder npath = new StringBuilder();
			this.buildNodePath(element, npath);
			// System.out.println("node-path:    " + npath);
			this.onElement(element, npath.toString());

			NodeList chs = element.getChildNodes();
			int len = chs.getLength();
			for (int i = 0; i < len; ++i) {
				Node ch = chs.item(i);
				if (ch.getNodeType() == Node.ELEMENT_NODE) {
					this.enum_nodes((Element) ch, depthLimit - 1);
				}
			}

		}

		private void onElement(Element element, String path) {
			if (path == null) {
			} else if (path.equals("/XRDS/XRD/Expires")) {
				this.onElementExpires(element);
			} else if (path.equals("/XRDS/XRD/Service")) {
				this.onElementService(element);
			} else {
			}
		}

		private void onElementService(Element element) {
			String type = this.getChildInnerText(element, "Type");
			String uri = this.getChildInnerText(element, "URI");
			if (type == null || uri == null) {
				return;
			}
			String type_ctrl_ep = "http://puyatech.com/api/snowflake/control-endpoint";
			if (type.equals(type_ctrl_ep)) {
				this._ctrl_ep = uri;
			}
		}

		private String getChildInnerText(Element element, String tag) {
			NodeList list = element.getElementsByTagName(tag);
			if (list == null)
				return null;
			if (list.getLength() <= 0)
				return null;
			Element ch = (Element) list.item(0);
			return this.getInnerText(ch);
		}

		private void onElementExpires(Element element) {
			String s = this.getInnerText(element);
			if (s.length() > 0)
				this._expires = Long.parseLong(s);
		}

		public String getInnerText(Element element) {
			Node ch = element.getFirstChild();
			for (; ch != null; ch = ch.getNextSibling()) {
				if (ch.getNodeType() == Node.TEXT_NODE) {
					String s = ch.getNodeValue().trim();
					if (s.length() > 0) {
						return s;
					}
				}
			}
			return null;
		}

		private void buildNodePath(Node node, StringBuilder sb) {
			if (node == null)
				return;
			if (node.getNodeType() != Node.ELEMENT_NODE)
				return;
			Element ele = (Element) node;
			this.buildNodePath(ele.getParentNode(), sb);
			String tag = ele.getTagName();
			int index = tag.indexOf(':');
			if (index >= 0) {
				tag = tag.substring(index + 1);
			}
			sb.append("/" + tag);
		}

		public String getControlEP() {
			return this._ctrl_ep;
		}

	}

	private class MyXrdsLoader {

		private Document _xrds;
		private String _next_uri;
		private final HttpClient _http;

		public MyXrdsLoader(HttpClient http) {
			this._http = http;
		}

		public Document getXRDS() {
			return this._xrds;
		}

		public String nextURI() {
			return this._next_uri;
		}

		public boolean load(String uri) {
			try {
				HttpGet htget = new HttpGet(uri);
				HttpResponse resp = _http.execute(htget);
				StatusLine status = resp.getStatusLine();
				if (status.getStatusCode() == 200) {
					HttpEntity entity = resp.getEntity();
					String type = entity.getContentType().getValue();
					if (type == null) {
					} else if (type.startsWith("text/html")) {
						this.loadHtml(resp, entity);
					} else if (type.startsWith("application/xrds+xml")) {
						this.loadXrds(resp, entity);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (this._xrds != null);
		}

		private void loadXrds(HttpResponse resp, HttpEntity entity)
				throws ParserConfigurationException, IllegalStateException,
				IOException, SAXException {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputStream in = entity.getContent();
			this._xrds = builder.parse(in);
			in.close();
		}

		private void loadHtml(HttpResponse resp, HttpEntity entity) {

			Header hdr = resp.getFirstHeader("X-XRDS-Location");
			if (hdr != null) {
				this._next_uri = hdr.getValue();
			}
		}
	}

}
