package ananas.lib.xgit.repo.remote;

import ananas.lib.xgit.repo.Repo;

public interface RemoteRepo extends Repo {

	RemoteRepoFactory getFactory();

	RemoteObjectBank getObjectBank();
}
