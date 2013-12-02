package ananas.waymq.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ananas.objectbox.session.IElement;
import ananas.objectbox.session.ISession;
import ananas.objectbox.session.JsonElementBase;
import ananas.xgit.repo.ObjectId;

import com.alibaba.fastjson.JSONObject;

public class User extends JsonElementBase {

	interface Key {
		// static
		String phone = "phone";
		// var
		String name = "name";
		String holdingGroups = "holdingGroups";
	}

	private ObjectId _phone;
	private String _name;
	private Set<ObjectId> _holding_group;

	public User() {
		this._holding_group = new HashSet<ObjectId>();
	}

	@Override
	protected void onLoad(JSONObject json, Map<String, String> header) {
		// static
		this._phone = Util.parseId(header.get(Key.phone));
		// var
		this._name = json.getString(Key.name);
		this._holding_group = Util.getIdSet(json, Key.holdingGroups);
	}

	@Override
	protected void onSave(JSONObject json) {
		json.put(Key.name, this._name);
		Util.putIdSet(json, Key.holdingGroups, this._holding_group);
	}

	public static User newInstance(Phone phone) {
		ISession session = phone.getTarget().getSession();
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

	public void holdGroup(Group group) {
		ObjectId id = group.getTarget().getId();
		if (!this._holding_group.contains(id)) {
			this._holding_group.add(id);
			this.getTarget().setModified(true);
		}
	}

	public Group[] listHoldingGroups() {
		List<Group> list = new ArrayList<Group>();
		ISession session = this.getTarget().getSession();
		Iterator<ObjectId> it = this._holding_group.iterator();
		for (; it.hasNext();) {
			ObjectId id = it.next();
			IElement ele = session.getElement(id);
			if (ele != null) {
				Group group = (Group) ele.getWrapper();
				list.add(group);
			}
		}
		return list.toArray(new Group[list.size()]);
	}

}
