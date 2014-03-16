package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRCore;

import com.alibaba.fastjson.JSONObject;

public class DefaultJRToken implements JRToken {

	private final RemoteRepo _repo;
	private HashID _token_id;

	public DefaultJRToken(RemoteRepo repo) {
		this._repo = repo;
	}

	@Override
	public NewToken doNewToken() {
		return new MyOpenToken("newToken");
	}

	@Override
	public OpenToken doOpenToken() {
		return new MyOpenToken("openToken");
	}

	@Override
	public GetToken doGetToken() {
		return new MyOpenToken("getToken");
	}

	private class MyOpenToken implements OpenToken, GetToken, NewToken {

		private final String _method;
		private HashID _token_id;
		private JSONObject _resp;

		public MyOpenToken(String method) {
			this._method = method;
		}

		@Override
		public boolean execute() {
			JSONRequest req = _repo.createJSONRequest();
			String idstr = (_token_id == null) ? null : _token_id.toString();
			req.setTarget(this._method, JRToken.class_name, idstr);
			JSONObject resp = req.request();
			this._resp = resp;
			if (resp.getBooleanValue(JRCore.Response.success)) {
				idstr = resp.getString(JRCore.Response.set_token);
				this._token_id = HashID.Factory.create(idstr);
				DefaultJRToken.this._token_id = this._token_id;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public HashID getTokenId() {
			return this._token_id;
		}

		@Override
		public JSONObject getResponse() {
			return this._resp;
		}

		@Override
		public void setToken(HashID token) {
			this._token_id = token;
		}
	}

	@Override
	public HashID getTokenId() {
		return this._token_id;
	}

}
