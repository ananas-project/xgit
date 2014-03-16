package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRCore;
import ananas.xgit3.core.util.Base64;

import com.alibaba.fastjson.JSONObject;

public class DefaultJRAuth implements JRAuth {

	private final RemoteRepo _repo;

	public DefaultJRAuth(RemoteRepo repo) {
		this._repo = repo;
	}

	@Override
	public RegisterAuth doRegister() {
		return new RegisterAuthImpl();
	}

	@Override
	public LoginAuth doLogin() {
		return new LoginAuthImpl();
	}

	@Override
	public LogoutAuth doLogout() {
		return new LogoutAuthImpl();
	}

	private class RegisterAuthImpl implements RegisterAuth {

		private String _user_name;
		private String _passwd;
		private String _mech;
		private String _step;
		private String _base64_auth;
		private String _base64_challenge;
		private JSONObject _resp;
		private HashID _token;

		@Override
		public boolean execute() {

			JSONRequest req = _repo.createJSONRequest();
			req.setTarget(JRAuth.method_register, JRAuth.class_name, null);
			req.setToken(this._token);
			req.setParameter(JRAuth.RegisterAuth.Request.mechanism, this._mech);
			req.setParameter(JRAuth.RegisterAuth.Request.step, _step);
			req.setParameter(JRAuth.RegisterAuth.Request.auth, _base64_auth);

			JSONObject resp = req.request();
			this._resp = resp;
			this._base64_challenge = resp
					.getString(JRAuth.RegisterAuth.Response.challenge);

			return (resp.getBooleanValue(JRCore.Response.success));
		}

		@Override
		public void setMechanism(String string) {
			this._mech = string;
		}

		@Override
		public JSONObject getResponse() {
			return this._resp;
		}

		@Override
		public void setToken(HashID token) {
			this._token = token;
		}

		@Override
		public void setAuth(byte[] auth) {
			if (auth == null)
				this._base64_auth = null;
			else
				this._base64_auth = Base64.encode(auth);
		}

		@Override
		public byte[] getChallenge() {
			String str = this._base64_challenge;
			if (str == null)
				return null;
			return Base64.decode(str);
		}
	}

	private class LoginAuthImpl implements LoginAuth {

		private JSONObject _resp;
		private HashID _token;

		@Override
		public boolean execute() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public JSONObject getResponse() {
			return this._resp;
		}

		@Override
		public void setToken(HashID token) {
			this._token = token;
		}
	}

	private class LogoutAuthImpl implements LogoutAuth {

		private JSONObject _resp;
		private HashID _token;

		@Override
		public boolean execute() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public JSONObject getResponse() {
			return this._resp;
		}

		@Override
		public void setToken(HashID token) {
			this._token = token;
		}
	}

}
