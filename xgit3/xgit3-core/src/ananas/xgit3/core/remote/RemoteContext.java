package ananas.xgit3.core.remote;

public interface RemoteContext {

	RemoteRepo getRepo(RemoteRepoInfo info);

	RemoteRepoInfo getRepoInfo(String uri);

	RemoteRepoFinder getFinder();

	class Factory {

		public static RemoteContext defaultContext() {
			return ThisModule.getModule().newDefaultContext();
		}

	}

}
