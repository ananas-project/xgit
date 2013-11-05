package ananas.xgit.repo;

import java.net.URI;

import ananas.lib.juke.Component;

public interface Repo extends Component {

	RepoFactory getFactory();

	boolean isBare();

	ObjectBank getObjectBank();

	URI getRepoURI();

	// CommandManager getCommandManager();

	// APIManager getAPIManager();

}
