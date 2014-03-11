package ananas.xgit3.core.remote;

import ananas.xgit3.core.local.LocalRepo;

public interface RemoteModule {

	RemoteRepoFinder newRemoteRepoFinder();

	RemoteRepoAgent newRemoteRepoAgent(LocalRepo aLocalRepo);

	RemoteRepo newRemoteRepo(RemoteRepoInfo info);

}
