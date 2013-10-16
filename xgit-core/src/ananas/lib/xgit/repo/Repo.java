package ananas.lib.xgit.repo;

import java.net.URI;

public interface Repo {

	RepoFactory getFactory();

	boolean isBare();

	ObjectBank getObjectBank();

	URI getRepoURI();

}
