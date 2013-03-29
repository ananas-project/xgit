package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.RefsManager;
import ananas.lib.xgit.Repository;

public class RefsManagerImpl extends AbstractDirectoryMonitor implements
		RefsManager {

	public RefsManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
