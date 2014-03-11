package ananas.xgit3.core.remote;

public interface RemoteRepoFinder {

	RemoteRepo findRepo(String uri);

	class Factory {

		public static RemoteRepoFinder create() {
			return ThisModule.getModule().newRemoteRepoFinder();
		}
	}

}
