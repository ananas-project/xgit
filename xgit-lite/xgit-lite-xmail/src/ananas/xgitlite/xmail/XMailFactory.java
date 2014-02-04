package ananas.xgitlite.xmail;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;

public interface XMailFactory {

	XCommitBuilder createBuilder(Repo repo);

	XCommit getCommit(ObjectId id);

}
