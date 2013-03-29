package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.LogsManager;
import ananas.lib.xgit.Repository;

public class LogsManagerImpl extends AbstractDirectoryMonitor implements
		LogsManager {

	public LogsManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
