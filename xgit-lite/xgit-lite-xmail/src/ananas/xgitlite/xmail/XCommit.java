package ananas.xgitlite.xmail;

import java.util.List;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalRepo;

public interface XCommit {

	List<Link> getLinks();

	ObjectId getId();

	LocalRepo getRepo();

	void push();

}
