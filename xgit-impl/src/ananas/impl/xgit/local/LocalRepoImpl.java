package ananas.impl.xgit.local;

import java.net.URI;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;

public class LocalRepoImpl implements LocalRepo {

	private final LocalRepoFactory _factory;
	private final VFile _repo_dir;
	private boolean _is_bare;
	private boolean _is_init = false;
	private LocalObjectBank _object_bank;

	public LocalRepoImpl(LocalRepoFactory factory, VFile file, boolean bare) {
		this._factory = factory;
		this._repo_dir = file;
		this._is_bare = bare;
	}

	@Override
	public boolean isBare() {
		return this._is_bare;
	}

	@Override
	public URI getRepoURI() {
		return this._repo_dir.getURI();
	}

	@Override
	public LocalRepoFactory getFactory() {
		return this._factory;
	}

	@Override
	public LocalObjectBank getObjectBank() {
		__init();
		return this._object_bank;
	}

	private void __init() {
		if (this._is_init) {
			return;
		}
		String name = "";
		VFile dir = this._repo_dir.getVFS().newFile(_repo_dir, name);
		this._object_bank = new LocalObjectBankImpl(dir);
		this._is_init = true;
	}

	@Override
	public VFile getRepoDirectory() {
		return this._repo_dir;
	}

}
