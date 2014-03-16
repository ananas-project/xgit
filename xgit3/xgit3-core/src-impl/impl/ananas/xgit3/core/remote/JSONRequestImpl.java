package impl.ananas.xgit3.core.remote;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONRequestImpl implements JSONRequest {

	private final RemoteRepo _repo;
	private final Map<String, String> _param;
	private String _http_method;

	public JSONRequestImpl(RemoteRepo repo) {
		this._repo = repo;
		this._param = new HashMap<String, String>();
	}

	@Override
	public void setParameter(String name, String value) {
		this.__set_param(name, value);
	}

	@Override
	public Map<String, String> getParameters() {
		return this._param;
	}

	@Override
	public JSONObject request(Map<String, String> param) {

		try {

			StringBuilder url = new StringBuilder();
			String ep = this._repo.getInfo().getControlEndpointURI();
			url.append(ep);
			List<String> keys = new ArrayList<String>(param.keySet());
			int cnt = keys.size();
			for (int i = 0; i < cnt; i++) {
				String key = keys.get(i);
				String value = param.get(key);
				key = URLEncoder.encode(key, "UTF-8");
				value = URLEncoder.encode(value, "UTF-8");
				if (i < 1) {
					url.append('?');
				} else {
					url.append('&');
				}
				url.append(key);
				url.append('=');
				url.append(value);
			}
			JSONObject json = null;
			HttpUriRequest htreq = this.makeHttpRequest(url.toString());
			HttpResponse resp = this._repo.getContext().getHttpClient()
					.execute(htreq);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
			resp.getEntity().writeTo(baos);
			String str = new String(baos.toByteArray(), "UTF-8");
			json = JSON.parseObject(str);

			json.put("request_uri", url.toString());
			String jstr = JSON.toJSONString(json, true);
			System.out.println(jstr);

			return json;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private HttpUriRequest makeHttpRequest(String uri) {
		String mtd = this._http_method;
		if (mtd == null) {
		} else if (mtd.equals("PUT")) {
			return new HttpPut(uri);
		} else if (mtd.equals("POST")) {
			return new HttpPost(uri);
		} else {
		}
		return new HttpGet(uri);
	}

	@Override
	public JSONObject request() {
		return this.request(_param);
	}

	@Override
	public void setToken(String token) {
		this.__set_param("token", token);
	}

	private void __set_param(String name, String value) {
		if (value == null) {
			_param.remove(name);
		} else {
			_param.put(name, value);
		}
	}

	@Override
	public void setTarget(String method, String aClassName, String id) {
		this.setMethod(method);
		this.setClass(aClassName);
		this.setId(id);
	}

	@Override
	public void setClass(String aClassName) {
		this.__set_param("class", aClassName);
	}

	@Override
	public void setId(String id) {
		this.__set_param("id", id);
	}

	@Override
	public void setMethod(String method) {
		this.__set_param("method", method);
	}

	@Override
	public void setHttpMethod(String method) {
		this._http_method = method;
	}

	@Override
	public void setToken(HashID token) {
		String str = null;
		if (token != null)
			str = token.toString();
		this.__set_param("token", str);
	}

}
