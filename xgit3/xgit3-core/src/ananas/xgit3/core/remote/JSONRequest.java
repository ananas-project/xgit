package ananas.xgit3.core.remote;

import java.util.Map;

import ananas.xgit3.core.HashID;

import com.alibaba.fastjson.JSONObject;

public interface JSONRequest {

	void setParameter(String name, String value);

	Map<String, String> getParameters();

	JSONObject request(Map<String, String> param);

	JSONObject request();

	void setToken(String token);

	void setToken(HashID token);

	void setTarget(String method, String aClassName, String id);

	void setClass(String aClassName);

	void setId(String id);

	void setMethod(String method);

	void setHttpMethod(String method);

}
