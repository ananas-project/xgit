package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.remote.RemoteRepo;

public class DefaultJRToken implements JRToken {

	private final RemoteRepo _repo;

	public DefaultJRToken(RemoteRepo repo) {
		this._repo = repo;
	}

	@Override
	public NewToken newToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpenToken openToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetToken getToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
