package ananas.xgitlite.impl;

import java.io.File;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoFinder;

public class XGitLiteImplementation extends XGitLite {

	@Override
	public ObjectId createObjectId(String id) {
		return ObjectIdImpl.create(id);
	}
	@Override
	public ObjectId createObjectId(byte[] id) {
		return ObjectIdImpl.create(id);
	}

	@Override
	public LocalRepoFinder getRepoFinder(boolean up) {
		return LocalRepoFinderFactory.getUpFinder();
	}

	@Override
	public String getDefaultRepoDirectoryName() {
		return ".git";
	}

	@Override
	public LocalRepo createLocalRepo(File path) {
		return new LocalRepoImpl(path);
	}


}
