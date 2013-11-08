package ananas.xgit.repo;

import java.net.URI;

public interface Repo {

	RepoFactory getFactory();

	boolean isBare();

	ObjectBank getObjectBank();

	URI getRepoURI();

	// CommandManager getCommandManager();

	// APIManager getAPIManager();

}
