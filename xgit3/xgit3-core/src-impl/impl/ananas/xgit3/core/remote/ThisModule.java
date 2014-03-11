package impl.ananas.xgit3.core.remote;

import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.remote.RemoteModule;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.RemoteRepoAgent;
import ananas.xgit3.core.remote.RemoteRepoFinder;
import ananas.xgit3.core.remote.RemoteRepoInfo;

public class ThisModule implements RemoteModule {

	@Override
	public RemoteRepoFinder newRemoteRepoFinder() {
		return new RemoteRepoFinderImpl();
	}

	@Override
	public RemoteRepoAgent newRemoteRepoAgent(LocalRepo aLocalRepo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteRepo newRemoteRepo(RemoteRepoInfo info) {
		return new RemoteRepoImpl(info);
	}

}
