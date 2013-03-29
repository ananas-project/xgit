package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.BranchesManager;
import ananas.lib.xgit.Repository;

public class BranchesManagerImpl extends AbstractDirectoryMonitor implements
		BranchesManager {

	public BranchesManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
