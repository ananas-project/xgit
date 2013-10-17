package ananas.xgit.repo;

import java.net.URI;

public interface RepoFactory {

	Repo createRepo(URI uri);

}
