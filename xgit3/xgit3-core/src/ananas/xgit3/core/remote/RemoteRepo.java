package ananas.xgit3.core.remote;

public interface RemoteRepo {

	RemoteContext getContext();

	RemoteRepoInfo getInfo();

	RemoteObjectBank getBank();

	JSONRequest createJSONRequest();

}
