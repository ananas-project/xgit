package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.WorkingDirectory;

public class WorkingDirectoryImpl extends AbstractDirectoryMonitor implements
		WorkingDirectory {

	public WorkingDirectoryImpl(Repository repo, VFile file) {
		super(repo, file);

	}

}
