package ananas.objectbox.body.json;

import ananas.objectbox.IObjectBody;

import com.alibaba.fastjson.JSONObject;

public interface IJsonBody extends IObjectBody {

	void onLoad(JSONObject root);

	JSONObject onSave(JSONObject root);

}
