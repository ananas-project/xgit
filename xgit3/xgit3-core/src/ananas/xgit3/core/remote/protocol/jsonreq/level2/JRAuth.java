package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRMethod;

public interface JRAuth {

	String class_name = "Auth";
	String method_register = "register";

	class Factory {

		public static JRAuth newInstance(RemoteRepo repo) {
			return new DefaultJRAuth(repo);
		}
	}

	RegisterAuth doRegister();

	LoginAuth doLogin();

	LogoutAuth doLogout();

	interface RegisterAuth extends JRMethod {

		interface Request {

			String mechanism = "mechanism";
			String step = "step";
			String data = "data"; // base64 binary

		}

		interface Response {

			String mechanism = "mechanism";
			String next_step = "next_step";
			String data = "data"; // base64 binary
			String result = "result"; // [ "success" | error ]

		}

		void setMechanism(String string);

		void setUserName(String string);

		void setPassword(String string);

	}

	interface LoginAuth extends JRMethod {

		interface Request {

			String mechanism = "mechanism";
			String step = "step";
			String data = "data"; // base64 binary

		}

		interface Response {

			String mechanism = "mechanism";
			String next_step = "next_step";
			String data = "data"; // base64 binary
			String result = "result"; // [ "success" | error ]

		}

	}

	interface LogoutAuth extends JRMethod {

		interface Request {

			String mechanism = "mechanism";
			String step = "step";
			String data = "data"; // base64 binary

		}

		interface Response {

			String mechanism = "mechanism";
			String next_step = "next_step";
			String data = "data"; // base64 binary
			String result = "result"; // [ "success" | error ]

		}

	}

}
