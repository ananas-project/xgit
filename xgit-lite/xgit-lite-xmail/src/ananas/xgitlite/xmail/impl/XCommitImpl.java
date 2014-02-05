package ananas.xgitlite.xmail.impl;

import java.util.List;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.xmail.Link;
import ananas.xgitlite.xmail.XCommit;

class XCommitImpl implements XCommit {

	private final LocalRepo _repo;
	private final ObjectId _id;

	public XCommitImpl(LocalRepo repo, ObjectId id) {
		this._repo = repo;
		this._id = id;
	}

	@Override
	public List<Link> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectId getId() {
		return this._id;
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

}
