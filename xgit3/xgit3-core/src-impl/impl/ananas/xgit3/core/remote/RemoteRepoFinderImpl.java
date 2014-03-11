package impl.ananas.xgit3.core.remote;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;

import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.RemoteRepoFinder;
import ananas.xgit3.core.remote.RemoteRepoInfo;

final class RemoteRepoFinderImpl implements RemoteRepoFinder {

	@Override
	public RemoteRepo findRepo(final String uri) {
		CloseableHttpClient http = HttpClients.createDefault();
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
		RemoteRepoInfo info = new MyRepoInfo(uri, doc);
		try {
			http.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RemoteRepo.Factory.create(info);
	}

	private class MyRepoInfo implements RemoteRepoInfo {

		public MyRepoInfo(String uri, Document doc) {
			// TODO Auto-generated constructor stub
		}
	}

	private class MyXrdsLoader {

		private Document _xrds;
		private String _next_uri;
		private final CloseableHttpClient _http;

		public MyXrdsLoader(CloseableHttpClient http) {
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
				CloseableHttpResponse resp = _http.execute(htget);
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
				resp.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (this._xrds != null);
		}

		private void loadXrds(CloseableHttpResponse resp, HttpEntity entity) {
			// TODO Auto-generated method stub

		}

		private void loadHtml(CloseableHttpResponse resp, HttpEntity entity) {
			// TODO Auto-generated method stub

		}
	}

}
