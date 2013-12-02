package ananas.waymq.element;

import java.util.HashMap;
import java.util.Map;

import ananas.objectbox.IObject;
import ananas.objectbox.session.IElement;
import ananas.objectbox.session.ISession;
import ananas.objectbox.session.JsonElementBase;
import ananas.xgit.repo.ObjectId;

import com.alibaba.fastjson.JSONObject;

public class Phone extends JsonElementBase {

	interface Key {
		// static
		String number = "number";
		// var
		String user = "user";
	}

	private ObjectId _user_id;
	private String _number;

	public Phone() {
	}

	@Override
	protected void onLoad(JSONObject json, Map<String, String> header) {
		// static
		this._number = header.get(Key.number);
		// var
		this._user_id = Util.parseId(json.getString(Key.user));
	}

	@Override
	protected void onSave(JSONObject json) {
		json.put(Key.user, Util.toString(this._user_id));
	}

	public static Phone newInstance(ISession session, String num) {
		Map<String, String> header = new HashMap<String, String>();
		header.put(Key.number, num);
		header.put(IObject.HeadKey.create_time, "0");
		IElement ele = session.createElement(Phone.class, header);
		return (Phone) ele.getWrapper();
	}

	public void setUser(User user) {
		this._user_id = user.getTarget().getId();
		this.getTarget().setModified(true);
	}

	public User getUser() {
		return this.__get_user(false);
	}

	public User getUser(boolean create) {
		return this.__get_user(create);
	}

	private User __get_user(boolean create) {
		IElement ele = this.getElement(this._user_id);
		if (ele != null) {
			return (User) ele.getWrapper();
		}
		if (!create) {
			return null;
		}
		User user = User.newInstance(this);
		this._user_id = user.getTarget().getId();
		return user;
	}

	public String getNumber() {
		return this._number;
	}

}
