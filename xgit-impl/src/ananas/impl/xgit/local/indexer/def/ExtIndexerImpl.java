package ananas.impl.xgit.local.indexer.def;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.io.vfs.VPathAbsolute;
import ananas.lib.io.vfs.VPathRelative;
import ananas.xgit.XGitException;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexerImpl implements Indexer {

	private final LocalRepo _repo;
	private final VFile _db_file;

	// private final VPathAbsolute _db_path;
	private final VPathAbsolute _wk_path;
	private final VPathAbsolute _rp_path;

	public ExtIndexerImpl(LocalRepo repo, VFile indexFile) {
		this._repo = repo;
		this._db_file = indexFile;

		VFileSystem vfs = indexFile.getVFS();

		// this._db_path = vfs.newAbsolutePath(indexFile.getAbsolutePath());
		this._wk_path = vfs.newAbsolutePath(repo.getWorkingDirectory()
				.getDirectory().getAbsolutePath());
		this._rp_path = vfs.newAbsolutePath(repo.getRepoDirectory()
				.getAbsolutePath());

	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public VFile getIndexDB() {
		return this._db_file;
	}

	@Override
	public IndexNode getNode(VFile file) throws XGitException {

		VFileSystem vfs = file.getVFS();
		VPathAbsolute fullpath = vfs.newAbsolutePath(file.getAbsolutePath());

		if (!fullpath.isSubOf(this._wk_path)) {
			throw new XGitException("the file not in the working dir : " + file);
		}
		if (fullpath.isSubOf(this._rp_path)) {
			throw new XGitException("the file in the repo dir : " + file);
		}

		VPathRelative offset = fullpath.getOffset(this._wk_path);
		VFile meta = vfs.newFile(this._db_file, offset.toString());

		return new ExtIndexNodeImpl(this, file, meta);
	}

}
