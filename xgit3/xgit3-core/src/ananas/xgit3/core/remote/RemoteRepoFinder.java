package ananas.xgit3.core.remote;

import java.net.URI;

public interface RemoteRepoFinder {

	RemoteRepoInfo findRepoInfo(URI uri);

}
