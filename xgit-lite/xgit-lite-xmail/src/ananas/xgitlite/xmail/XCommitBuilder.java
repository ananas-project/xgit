package ananas.xgitlite.xmail;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XCommitBuilder {

	Map<String, Link> getLinkMap();

	Document getDocument();

	Element getHead();

	Element getBody();

	XCommit build();

}
