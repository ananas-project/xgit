package ananas.objectbox.ctrl.json;

import ananas.objectbox.IObjectCtrl;

import com.alibaba.fastjson.JSONObject;

public interface IJsonController extends IObjectCtrl {

	void onLoad(JSONObject root);

	JSONObject onSave(JSONObject root);

}
