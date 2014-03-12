package impl.ananas.xgit3.core.remote;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import ananas.xgit3.core.remote.RemoteContext;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.RemoteRepoFinder;
import ananas.xgit3.core.remote.RemoteRepoInfo;

public class BaseRemoteContext implements RemoteContext {

	private final HttpClient _http;

	public BaseRemoteContext() {
		this._http = HttpClients.createDefault();
	}

	@Override
	public RemoteRepo getRepo(RemoteRepoInfo info) {
		return new RemoteRepoImpl(info);
	}

	@Override
	public RemoteRepoInfo getRepoInfo(String uri) {
		return this.getFinder().findRepoInfo(uri);
	}

	@Override
	public HttpClient getHttpClient() {
		return this._http;
	}

	@Override
	public RemoteRepoFinder getFinder() {
		return new RemoteRepoFinderImpl(this);
	}

}
