package ananas.lib.xgit.impl.extension;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.extension.XGitObjectsMetaDataManager;
import ananas.lib.xgit.impl.AbstractDirectoryMonitor;

public class MetaDataManagerImpl extends AbstractDirectoryMonitor implements
		XGitObjectsMetaDataManager {

	public MetaDataManagerImpl(Repository repo, VFile file) {
		super(repo, file);
		// TODO Auto-generated constructor stub
	}

}
