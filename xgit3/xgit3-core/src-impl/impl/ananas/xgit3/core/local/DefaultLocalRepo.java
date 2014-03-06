package impl.ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalRepo;

public class DefaultLocalRepo implements LocalRepo {

	private final File _path;
	private LocalObjectBank _bank;

	public DefaultLocalRepo(File path) {
		this._path = path;
	}

	@Override
	public File getPath() {
		return this._path;
	}

	@Override
	public LocalObjectBank getBank() {
		LocalObjectBank bank = this._bank;
		if (bank == null) {
			File path = new File(this._path, "objects");
			bank = new DefaultBank(path, null, 0);
			this._bank = bank;
		}
		return bank;
	}

}
