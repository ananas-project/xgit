package ananas.xgitlite.xmail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.xmail.Link;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XMail;
import ananas.xgitlite.xmail.XMailException;

class XCommitImpl implements XCommit {

	private final LocalRepo _repo;
	private final ObjectId _id;
	private MyData _data;

	public XCommitImpl(LocalRepo repo, ObjectId id) {
		this._repo = repo;
		this._id = id;
	}

	@Override
	public List<Link> getLinks() {
		MyData data = null;
		try {
			data = this.__get_data();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.getLinks();
	}

	private MyData __get_data() throws SAXException, IOException,
			XMailException {
		MyData data = this._data;
		if (data == null) {
			data = new MyData();
			data.load();
			this._data = data;
		}
		return data;
	}

	@Override
	public ObjectId getId() {
		return this._id;
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

	class MyData {

		private Document _doc;

		public List<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		public void load() throws SAXException, IOException, XMailException {

			XCommitImpl pthis = XCommitImpl.this;
			ObjectId id = pthis._id;
			LocalObjectBank bank = pthis._repo.getObjectBank();
			LocalObject obj = bank.getObject(id);
			InputStream in = obj.openPlainInputStream();

			DocumentBuilder xbuilder = null;
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				factory.setValidating(false);
				factory.setNamespaceAware(true);
				xbuilder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			Document doc = xbuilder.parse(in);
			this._doc = doc;

			Element root = doc.getDocumentElement();

			this.__on_root(root);

		}

		private void __on_root(Element root) throws XMailException {
			System.out.println("<" + XMail.LocalName.xmail + ">");

			DomUtil.check(root, XMail.xmlns, XMail.LocalName.xmail);
			NodeList list = root.getChildNodes();
			int cnt = list.getLength();
			for (int i = 0; i < cnt; i++) {
				Node ch = list.item(i);
				if (ch.getNodeType() != Node.ELEMENT_NODE) {
					// skip
				} else if (DomUtil.isNamedOf(ch, XMail.xmlns,
						XMail.LocalName.head)) {
					this.__on_head((Element) ch);
				} else if (DomUtil.isNamedOf(ch, XMail.xmlns,
						XMail.LocalName.body)) {
					this.__on_body((Element) ch);
				} else {
					// skip
				}
			}
		}

		private void __on_body(Element node) {
			// TODO Auto-generated method stub
			System.out.println("<" + XMail.LocalName.body + ">");

		}

		private void __on_head(Element head) throws XMailException {

			System.out.println("<" + XMail.LocalName.head + ">");

			DomUtil.check(head, XMail.xmlns, XMail.LocalName.head);
			NodeList list = head.getChildNodes();
			int cnt = list.getLength();
			for (int i = 0; i < cnt; i++) {
				Node ch = list.item(i);
				if (ch.getNodeType() != Node.ELEMENT_NODE) {
					// skip
				} else if (DomUtil.isNamedOf(ch, XMail.xmlns,
						XMail.LocalName.meta)) {
					this.__on_meta((Element) ch);
				} else if (DomUtil.isNamedOf(ch, XMail.xmlns,
						XMail.LocalName.link)) {
					this.__on_link((Element) ch);
				} else {
					// skip
				}
			}
		}

		private void __on_meta(Element ch) {
			// TODO Auto-generated method stub
			System.out.println("<" + XMail.LocalName.meta + ">");

		}

		private void __on_link(Element ch) {
			// TODO Auto-generated method stub
			System.out.println("<" + XMail.LocalName.link + ">");

		}
	}

}
