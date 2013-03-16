package ananas.lib.xgit;

public interface XGitRepository extends DirectoryMonitor {

	XGitWorkingDirectory getWorkingDirectory();

	XGitObjectsManager getObjectsManager();

	XGitExtendsDirectory getExtendsManager();

	class Factory {

		public static XGitRepositoryFactory getFactory() {
			return XGitRepositoryFactoryLoader.getFactory();
		}

	}

}
