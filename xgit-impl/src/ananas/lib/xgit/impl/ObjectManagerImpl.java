package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.ObjectsManager;
import ananas.lib.xgit.Repository;

public class ObjectManagerImpl extends AbstractDirectoryMonitor implements
		ObjectsManager {

	public ObjectManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
