package ananas.xgit.repo.remote;

import java.net.URI;

import ananas.lib.juke.Kernel;
import ananas.xgit.repo.RepoFactory;

public interface RemoteRepoFactory extends RepoFactory {

	RemoteRepo createRepo(Kernel kernel, URI uri);
}
