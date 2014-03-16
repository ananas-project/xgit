package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRCore;

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
		private String _base64_data;
		private JSONObject _resp;
		private HashID _token;

		@Override
		public boolean execute() {

			JSONRequest req = _repo.createJSONRequest();
			req.setTarget(JRAuth.method_register, JRAuth.class_name, null);
			req.setToken(this._token);
			req.setParameter(JRAuth.RegisterAuth.Request.mechanism, this._mech);
			req.setParameter(JRAuth.RegisterAuth.Request.step, this._step);
			req.setParameter(JRAuth.RegisterAuth.Request.data,
					this._base64_data);

			JSONObject resp = req.request();
			this._resp = resp;
			return (resp.getBooleanValue(JRCore.Response.success));
		}

		@Override
		public void setMechanism(String string) {
			this._mech = string;
		}

		@Override
		public void setUserName(String username) {
			this._user_name = username;
		}

		@Override
		public void setPassword(String string) {
			this._passwd = string;
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
