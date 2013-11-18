package ananas.objectbox.body.json;

import com.alibaba.fastjson.JSONObject;

public interface JsonBody {

	void onLoad(JSONObject root);

	JSONObject onSave(JSONObject root);
}
