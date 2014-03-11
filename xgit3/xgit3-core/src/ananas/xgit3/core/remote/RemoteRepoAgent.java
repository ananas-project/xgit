package ananas.xgit3.core.remote;

import ananas.xgit3.core.local.LocalRepo;

public interface RemoteRepoAgent {

	RemoteRepo getRepo(String uri);

	class Factory {

		public static RemoteRepoAgent create(LocalRepo aLocalRepo) {
			return ThisModule.getModule().newRemoteRepoAgent(aLocalRepo);
		}
	}
}
