package ananas.xgit3.core.remote.protocol.jsonreq.level2;

import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRMethod;

public interface JRToken {

	String class_name = "Token";

	class Factory {

		public static JRToken newInstance(RemoteRepo repo) {
			return new DefaultJRToken(repo);
		}

	}

	NewToken newToken();

	OpenToken openToken();

	GetToken getToken();

	interface NewToken extends JRMethod {

		interface Request {
		}

		interface Response {

			String set_token = "set_token";

		}

		String getTokenId();
	}

	interface OpenToken extends JRMethod {

		interface Request {
		}

		interface Response {

			String set_token = "set_token";

		}

		String getTokenId();

	}

	interface GetToken extends JRMethod {

		interface Request {
		}

		interface Response {

			String set_token = "set_token";

		}

		String getTokenId();

	}

}
