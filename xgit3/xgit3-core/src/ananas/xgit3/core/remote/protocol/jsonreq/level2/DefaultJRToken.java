package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRCore;

import com.alibaba.fastjson.JSONObject;

public class DefaultJRToken implements JRToken {

	private final RemoteRepo _repo;

	public DefaultJRToken(RemoteRepo repo) {
		this._repo = repo;
	}

	@Override
	public NewToken newToken() {
		return new MyOpenToken("newToken");
	}

	@Override
	public OpenToken openToken() {
		return new MyOpenToken("openToken");
	}

	@Override
	public GetToken getToken() {
		return new MyOpenToken("getToken");
	}

	private class MyOpenToken implements OpenToken, GetToken, NewToken {

		private String _token_id;
		private final String _method;

		public MyOpenToken(String method) {
			this._method = method;
		}

		@Override
		public boolean execute() {
			JSONRequest req = _repo.createJSONRequest();
			req.setTarget(this._method, JRToken.class_name, this._token_id);
			JSONObject resp = req.request();
			if (resp.getBooleanValue(JRCore.Response.success)) {
				this._token_id = resp.getString(JRCore.Response.set_token);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String getTokenId() {
			return this._token_id;
		}
	}

}
