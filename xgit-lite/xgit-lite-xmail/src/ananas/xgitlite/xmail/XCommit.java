package ananas.xgitlite.xmail;

import java.util.List;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;

public interface XCommit {

	List<Link> getLinks();

	ObjectId getId();

	Repo getRepo();

}
