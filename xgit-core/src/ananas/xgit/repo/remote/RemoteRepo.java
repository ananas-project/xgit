package ananas.xgit.repo.remote;

import ananas.xgit.repo.Repo;

public interface RemoteRepo extends Repo {

	RemoteRepoFactory getFactory();

	RemoteObjectBank getObjectBank();
}
