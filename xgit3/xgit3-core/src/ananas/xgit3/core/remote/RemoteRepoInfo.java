package ananas.xgit3.core.remote;

import java.net.URI;

public interface RemoteRepoInfo {

	RemoteContext getContext();

	// String getControlEndpointURI();

	URI getRepoURI();

	long getExpires();

}
