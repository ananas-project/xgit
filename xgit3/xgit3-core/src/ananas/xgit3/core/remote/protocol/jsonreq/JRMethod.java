package ananas.xgit3.core.remote.protocol.jsonreq;

import ananas.xgit3.core.HashID;

import com.alibaba.fastjson.JSONObject;

public interface JRMethod {

	void setToken(HashID token);

	boolean execute();

	JSONObject getResponse();

}
