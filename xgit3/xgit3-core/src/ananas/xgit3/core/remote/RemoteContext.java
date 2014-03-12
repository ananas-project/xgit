package ananas.xgit3.core.remote;

import org.apache.http.client.HttpClient;

public interface RemoteContext {

	RemoteRepo getRepo(RemoteRepoInfo info);

	RemoteRepoInfo getRepoInfo(String uri);

	HttpClient getHttpClient();

	RemoteRepoFinder getFinder();

	class Factory {

		public static RemoteContext defaultContext() {
			return ThisModule.getModule().newDefaultContext();
		}

	}

}
