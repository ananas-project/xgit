package ananas.xgitlite.xmail.impl;

import org.w3c.dom.Node;

import ananas.xgitlite.xmail.XMailException;

public class DomUtil {

	public static void check(Node node, String nsURI, String localName)
			throws XMailException {

		if (isNamedOf(node, nsURI, localName)) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("the name is not match.");
		throw new XMailException(sb.toString());
	}

	public static boolean isNamedOf(Node node, String nsURI, String localName) {

		String ln = node.getLocalName();
		String ns = node.getNamespaceURI();

		if (!localName.equals(ln)) {
			return false;
		}
		if (!nsURI.equals(ns)) {
			return false;
		}
		return true;
	}

}
