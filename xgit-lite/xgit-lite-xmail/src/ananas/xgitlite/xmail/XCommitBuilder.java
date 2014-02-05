package ananas.xgitlite.xmail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ananas.xgitlite.XGLException;
import ananas.xgitlite.local.LocalRepo;

public interface XCommitBuilder {

	Map<String, Link> getLinkMap();

	Document getDocument();

	Element getHead();

	Element getBody();

	XCommit build() throws IOException, XGLException;

	LocalRepo getRepo();

	Link addLink(File file) throws IOException, XGLException;

}
