package ananas.xgitlite.xmail;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ananas.xgitlite.Repo;

public interface XCommitBuilder {

	Map<String, Link> getLinkMap();

	Document getDocument();

	Element getHead();

	Element getBody();

	XCommit build();

	Repo getRepo();

	Link addLink(File file);

}
