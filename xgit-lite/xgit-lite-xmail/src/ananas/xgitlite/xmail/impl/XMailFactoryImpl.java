package ananas.xgitlite.xmail.impl;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XCommitBuilder;
import ananas.xgitlite.xmail.XMailFactory;
import ananas.xgitlite.xmail.client.XMailClientFactory;

public class XMailFactoryImpl implements XMailFactory {

	@Override
	public XCommitBuilder createBuilder(LocalRepo repo) {
		return new XCommitBuilderImpl(repo);
	}

	@Override
	public XCommit getCommit(LocalRepo repo, ObjectId id) {
		return new XCommitImpl(repo, id);
	}

	@Override
	public XMailClientFactory getXMailClientFactory() {
		return   XMailClientFactoryImpl .getDefault()  ;
	}

}
