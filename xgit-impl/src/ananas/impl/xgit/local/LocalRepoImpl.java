package ananas.impl.xgit.local;

import java.net.URI;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;

public class LocalRepoImpl implements LocalRepo {

	public LocalRepoImpl(VFile file, boolean bare) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isBare() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public URI getRepoURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalRepoFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalObjectBank getObjectBank() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VFile getRepoDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

}
