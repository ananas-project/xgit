package ananas.xgit3.core.remote;

import java.net.URI;

public interface RemoteContext {

	RemoteRepo getRepo(RemoteRepoInfo info);

	RemoteRepoInfo getRepoInfo(URI uri);

	RemoteRepoFinder getFinder();

	class Factory {

		public static RemoteContext defaultContext() {
			return ThisModule.getModule().newDefaultContext();
		}

	}

}
