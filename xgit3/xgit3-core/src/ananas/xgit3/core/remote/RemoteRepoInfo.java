package ananas.xgit3.core.remote;

public interface RemoteRepoInfo {

	RemoteContext getContext();

	String getControlEndpointURI();

	String getRepoURI();

	long getExpires();

}
