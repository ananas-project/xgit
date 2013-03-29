package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.HooksManager;
import ananas.lib.xgit.Repository;

public class HooksManagerImpl extends AbstractDirectoryMonitor implements
		HooksManager {

	public HooksManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
