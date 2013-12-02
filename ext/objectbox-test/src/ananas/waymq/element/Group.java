package ananas.waymq.element;

import java.util.HashMap;
import java.util.Map;

import ananas.objectbox.session.IElement;
import ananas.objectbox.session.ISession;
import ananas.objectbox.session.JsonElementBase;

import com.alibaba.fastjson.JSONObject;

public class Group extends JsonElementBase {

	interface Key {
		// static
		String owner = "owner";
		// var
		String user = "user";
	}

	public Group() {
	}

	public static Group newInstance(User owner) {
		ISession session = owner.getTarget().getSession();
		Map<String, String> header = new HashMap<String, String>();
		header.put(Key.owner, owner.getTarget().getId() + "");
		IElement ele = session.createElement(Group.class, header);
		Group group = (Group) ele.getWrapper();
		owner.holdGroup(group);
		return group;
	}

	@Override
	protected void onLoad(JSONObject json, Map<String, String> header) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSave(JSONObject json) {
		// TODO Auto-generated method stub

	}

}
