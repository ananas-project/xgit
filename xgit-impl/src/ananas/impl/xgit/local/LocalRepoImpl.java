package ananas.impl.xgit.local;

import java.net.URI;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.local.ExtIndexBank;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;
import ananas.xgit.repo.local.WorkingDirectory;

public class LocalRepoImpl implements LocalRepo {

	private final LocalRepoFactory _factory;
	private final VFile _repo_dir;
	private final boolean _is_bare;
	private boolean _is_init = false;
	private LocalObjectBank _object_bank;
	private ExtIndexBank _ext_index_bank;
	private WorkingDirectory _working_directory;

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

		VFileSystem vfs = this._repo_dir.getVFS();
		this._object_bank = new LocalObjectBankImpl(vfs.newFile(_repo_dir,
				"objects"));
		this._ext_index_bank = new ExtIndexBankImpl(this, vfs.newFile(
				_repo_dir, "xgit/index"));
		this._working_directory = _is_bare ? null : (new WorkingDirectoryImpl(
				_repo_dir.getParentFile()));

		this._is_init = true;
	}

	@Override
	public VFile getRepoDirectory() {
		return this._repo_dir;
	}

	public String toString() {
		return ("[" + this.getClass().getSimpleName() + " " + this._repo_dir + "]");
	}

	@Override
	public ExtIndexBank getExtIndexBank() {
		__init();
		return this._ext_index_bank;
	}

	@Override
	public WorkingDirectory getWorkingDirectory() {
		__init();
		return this._working_directory;
	}

}
