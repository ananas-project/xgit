package ananas.lib.xgit.repo.remote;

import java.net.URI;

import ananas.lib.xgit.repo.RepoFactory;

public interface RemoteRepoFactory extends RepoFactory {

	RemoteRepo createRepo(URI uri);
}
