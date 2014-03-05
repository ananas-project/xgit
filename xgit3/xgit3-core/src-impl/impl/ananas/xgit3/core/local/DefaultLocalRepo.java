package impl.ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalRepo;

public class DefaultLocalRepo implements LocalRepo {

	private final File _path;

	public DefaultLocalRepo(File path) {
		this._path = path;
	}

	@Override
	public File getPath() {
		return this._path;
	}

	@Override
	public LocalObjectBank getBank() {
		// TODO Auto-generated method stub
		return null;
	}

}
