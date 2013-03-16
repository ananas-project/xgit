package ananas.lib.xgit.extension;

import ananas.lib.xgit.DirectoryMonitor;

public interface XGitExtensionsDirectory extends DirectoryMonitor {

	XGitIndexFile getIndexFile();

	XGitIndexManager getIndexManager();

	XGitObjectsMetaDataManager getObjectsMetaDataManager();

}
