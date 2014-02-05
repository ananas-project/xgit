package ananas.xgitlite.xmail;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalRepo;

public interface XMailFactory {

	XCommitBuilder createBuilder(LocalRepo repo);

	XCommit getCommit(LocalRepo repo, ObjectId id);

}
