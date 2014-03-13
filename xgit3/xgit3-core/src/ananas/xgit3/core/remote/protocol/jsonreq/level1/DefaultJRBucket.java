package ananas.xgit3.core.remote.protocol.jsonreq.level1;

import java.util.ArrayList;
import java.util.List;

import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.protocol.jsonreq.JRCore;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DefaultJRBucket implements JRBucket {

	private final RemoteRepo _repo;

	public DefaultJRBucket(RemoteRepo repo) {
		this._repo = repo;
	}

	@Override
	public GetInfo getInfo() {
		return new MyGetInfo();
	}

	class MyGetInfo implements GetInfo {

		private String _id;
		private String _address;
		private JSONObject _resp;

		@Override
		public boolean execute() {
			JSONRequest req = _repo.createJSONRequest();
			req.setClass(JRBucket.class_name);
			req.setMethod(JRBucket.GetInfo.method_name);
			req.setId(this._id);
			req.setParameter(JRBucket.GetInfo.Request.address, this._address);
			JSONObject json = req.request();
			this._resp = json;
			return json.getBooleanValue(JRCore.Response.success);
		}

		@Override
		public String getControlEndpointURI() {
			return this._resp
					.getString(JRBucket.GetInfo.Response.control_endpoint);
		}

		@Override
		public String getBucketId() {
			return this._resp.getString(JRBucket.GetInfo.Response.id);
		}

		@Override
		public List<String> getAddressList() {
			JSONArray array = this._resp
					.getJSONArray(JRBucket.GetInfo.Response.address_list);
			List<String> list = new ArrayList<String>();
			Object[] a2 = array.toArray();
			for (Object it : a2) {
				if (it instanceof String) {
					list.add((String) it);
				}
			}
			return list;
		}

		@Override
		public String getName() {
			return this._resp.getString(JRBucket.GetInfo.Response.name);
		}

		@Override
		public String getDataEndpointURI() {
			return this._resp
					.getString(JRBucket.GetInfo.Response.data_endpoint);
		}

		@Override
		public void setId(String id) {
			this._id = id;
		}

		@Override
		public void setAddress(String addr) {
			this._address = addr;
		}
	}

}
