package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.extension.XGitExtensionsDirectory;

public interface Repository extends BaseRepository {

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

	class Factory {

		public static Repository open(VFile file, boolean bare) {
			XGitEnvironment envi = new DefaultXGitEnvironment();
			return envi.openRepository(file, bare);
		}

		public static Repository create(VFile file, boolean bare) {
			XGitEnvironment envi = new DefaultXGitEnvironment();
			return envi.createNewRepository(file, bare);
		}

		public static XGitEnvironment getEnvironment() {
			XGitEnvironment envi = new DefaultXGitEnvironment();
			return envi;
		}

	}

}
