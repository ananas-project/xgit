package ananas.xgitlite.impl;

import java.io.File;

import ananas.xgitlite.LocalRepo;
import ananas.xgitlite.LocalRepoFinder;
import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGitLite;

public class XGitLiteImplementation extends XGitLite {

	@Override
	public ObjectId createObjectId(String id) {
		return ObjectIdImpl.create(id);
	}

	@Override
	public LocalRepoFinder getRepoFinder(boolean up) {
		return LocalRepoFinderFactory.getUpFinder();
	}

	@Override
	public String getDefaultRepoDirectoryName() {
		return ".xgit";
	}

	@Override
	public LocalRepo createLocalRepo(File path) {
		return new LocalRepoImpl(path);
	}

}
