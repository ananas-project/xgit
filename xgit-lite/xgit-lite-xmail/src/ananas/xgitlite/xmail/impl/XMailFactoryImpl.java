package ananas.xgitlite.xmail.impl;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XCommitBuilder;
import ananas.xgitlite.xmail.XMailFactory;

public class XMailFactoryImpl implements XMailFactory {

	@Override
	public XCommitBuilder createBuilder(Repo repo) {
		return new XCommitBuilderImpl(repo);
	}

	@Override
	public XCommit getCommit(ObjectId id) {
		return new XCommitImpl(id);
	}

}
