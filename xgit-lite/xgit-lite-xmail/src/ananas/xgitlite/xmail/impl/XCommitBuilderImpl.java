package ananas.xgitlite.xmail.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import ananas.xgitlite.XGLException;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.xmail.DefaultLink;
import ananas.xgitlite.xmail.Link;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XCommitBuilder;
import ananas.xgitlite.xmail.XMail;

class XCommitBuilderImpl implements XCommitBuilder {

	private final Map<String, Link> _link_map;
	private final Document _doc;
	private Element _head;
	private Element _body;
	private final LocalRepo _repo;

	public XCommitBuilderImpl(LocalRepo repo) {
		_repo = repo;
		_link_map = new HashMap<String, Link>();

		Document doc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.newDocument();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		_doc = doc;
		String uri = XMail.xmlns;
		_head = doc.createElementNS(uri, "head");
		_body = doc.createElementNS(uri, "body");
		Element root = doc.createElementNS(uri, "xmail");
		doc.appendChild(root);
		root.appendChild(_head);
		root.appendChild(_body);
	}

	@Override
	public Map<String, Link> getLinkMap() {
		return this._link_map;
	}

	@Override
	public Document getDocument() {
		return this._doc;
	}

	@Override
	public Element getHead() {
		return this._head;
	}

	@Override
	public Element getBody() {
		return this._body;
	}

	@Override
	public XCommit build() throws IOException, XGLException {

		DOMImplementationLS ls = (DOMImplementationLS) this._doc
				.getImplementation().getFeature("LS", "3.0");

		LSSerializer ser = ls.createLSSerializer();

		String text = ser.writeToString(_doc);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer wtr = new OutputStreamWriter(baos);
		wtr.write(text);
		wtr.flush();
		wtr.close();
		byte[] ba = baos.toByteArray();

		LocalObjectBank bank = this._repo.getObjectBank();
		LocalObject obj = bank.addObject(XMail.mime_type, ba.length,
				new ByteArrayInputStream(ba));

		return new XCommitImpl(_repo, obj.getId());

	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public Link addLink(File file) throws IOException, XGLException {
		LocalObjectBank objbank = _repo.getObjectBank();
		LocalObject obj = objbank.addObject(XMail.mime_type, file);
		return new DefaultLink(obj);
	}
}
