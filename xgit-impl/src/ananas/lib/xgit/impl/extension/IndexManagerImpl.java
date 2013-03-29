package ananas.lib.xgit.impl.extension;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.extension.XGitIndexManager;
import ananas.lib.xgit.impl.AbstractDirectoryMonitor;

public class IndexManagerImpl extends AbstractDirectoryMonitor implements
		XGitIndexManager {

	public IndexManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
