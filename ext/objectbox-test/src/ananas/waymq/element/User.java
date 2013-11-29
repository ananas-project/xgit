package ananas.waymq.element;

import java.util.HashMap;
import java.util.Map;

import ananas.objectbox.session.IElement;
import ananas.objectbox.session.ISession;
import ananas.objectbox.session.JsonElementBase;
import ananas.xgit.repo.ObjectId;

import com.alibaba.fastjson.JSONObject;

public class User extends JsonElementBase {

	interface Key {
		String phone = "phone";
		String name = "name";
	}

	private ObjectId _phone;
	private String _name;

	public User() {
	}

	@Override
	protected void onLoad(JSONObject json, Map<String, String> header) {
		// static
		this._phone = Util.parseId(header.get(Key.phone));
		// var
		this._name = json.getString(Key.name);
	}

	@Override
	protected void onSave(JSONObject json) {
		json.put(Key.name, this._name);
	}

	public static User newInstance(ISession session, Phone phone) {
		Map<String, String> header = new HashMap<String, String>();
		header.put(Key.phone, phone.getTarget().getId() + "");
		IElement ele = session.createElement(User.class, header);
		User user = (User) ele.getWrapper();
		phone.setUser(user);
		return user;
	}

	public Phone getPhone() {
		IElement ele = this.getElement(this._phone);
		if (ele == null)
			return null;
		return (Phone) ele.getWrapper();
	}

	public String getName() {
		return this._name;
	}

	public void setName(String name) {
		this._name = name;
		this.getTarget().setModified(true);
	}

}
