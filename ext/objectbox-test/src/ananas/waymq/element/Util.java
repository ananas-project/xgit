package ananas.waymq.element;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import ananas.xgit.repo.ObjectId;

public class Util {

	public static ObjectId parseId(String s) {
		if (s == null)
			return null;
		return ObjectId.Factory.create(s);
	}

	public static String toString(ObjectId id) {
		if (id == null)
			return null;
		else
			return id.toString();
	}

	public static Set<ObjectId> getIdSet(JSONObject json, String key ) {
		// TODO Auto-generated method stub
		return null;
	}

	public static  void putIdSet(JSONObject json, String key , Set<ObjectId> idSet ) {
		// TODO Auto-generated method stub
	
	}

}
