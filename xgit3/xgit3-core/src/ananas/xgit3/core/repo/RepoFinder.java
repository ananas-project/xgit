package ananas.xgit3.core.repo;

import java.net.URI;

public interface RepoFinder {

	RepoInfo discover(RepoContext context, URI uri);

}
