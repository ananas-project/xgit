package ananas.xgit.repo.remote;

import java.net.URI;

import ananas.xgit.repo.RepoFactory;

public interface RemoteRepoFactory extends RepoFactory {

	RemoteRepo createRepo(URI uri);
}
