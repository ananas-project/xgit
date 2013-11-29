package ananas.waymq.element;

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

}
