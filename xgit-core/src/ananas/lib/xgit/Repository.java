package ananas.lib.xgit;

import ananas.lib.xgit.extension.XGitExtensionsDirectory;

public interface Repository extends DirectoryMonitor {

	// out of repos dir
	WorkingDirectory getWorkingDirectory();

	// in repos dir
	XGitExtensionsDirectory getXGitExtensionsManager();

	BranchesManager getBranchesManager();

	ObjectsManager getObjectsManager();

	LogsManager getLogsManager();

	HooksManager getHooksManager();

	RefsManager getRefsManager();

	ConfigFile getFileConfig();

	HeadFile getFileHEAD();

	// attributes
	boolean isBare();

	class Factory {

		public static RepositoryFactory getFactory() {
			return RepositoryFactoryLoader.getFactory();
		}

	}

}
