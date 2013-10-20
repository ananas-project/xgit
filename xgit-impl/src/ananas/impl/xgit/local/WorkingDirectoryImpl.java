package ananas.impl.xgit.local;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.local.WorkingDirectory;

public class WorkingDirectoryImpl implements WorkingDirectory {

	private final VFile _dir;

	public WorkingDirectoryImpl(VFile dir) {
		this._dir = dir;
	}

	@Override
	public VFile getDirectory() {
		return this._dir;
	}

}
