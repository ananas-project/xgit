package ananas.xgit3.core.remote.protocol.jsonreq;

public interface JRCore {

	interface Request {

		String class_ = "class";
		String id = "id";
		String method = "method";
		String token = "token";

		String http_method = "http_method";
		String http_address = "http_address";
	}

	interface Response {

		String set_token = "set_token";
		String exception = "exception";
		String error = "error";
		String success = "success";

	}

}
