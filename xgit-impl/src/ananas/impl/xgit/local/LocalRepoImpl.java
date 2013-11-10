package ananas.impl.xgit.local;

import java.net.URI;

import ananas.impl.xgit.local.indexer.def.ExtIndexerFactory;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.IndexerFactory;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;
import ananas.xgit.repo.local.WorkingDirectory;

public class LocalRepoImpl implements LocalRepo {

	private final LocalRepoFactory _factory;
	private final VFile _repo_dir;
	private final boolean _is_bare;
	private boolean _is_init = false;
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
		return this._repo_dir.toURI();
	}

	@Override
	public LocalRepoFactory getFactory() {
		return this._factory;
	}

	@Override
	public LocalObjectBank getObjectBank() {
		__init();
		VFile repo = this.getRepoDirectory();
		VFileSystem vfs = repo.getVFS();
		VFile dir = vfs.newFile(repo, "objects");
		return new LocalObjectBankImpl(dir);
	}

	private void __init() {
		if (this._is_init) {
			return;
		}

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
	public WorkingDirectory getWorkingDirectory() {
		__init();
		return this._working_directory;
	}

	@Override
	public Indexer getIndexer() {
		__init();
		IndexerFactory ifac = new ExtIndexerFactory();
		return ifac.create(this);
	}

}
