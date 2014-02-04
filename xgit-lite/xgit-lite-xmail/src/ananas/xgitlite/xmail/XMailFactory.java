package ananas.xgitlite.xmail;

import ananas.xgitlite.ObjectId;

public interface XMailFactory {

	XCommitBuilder createBuilder();

	XCommit getCommit(ObjectId id);

}
