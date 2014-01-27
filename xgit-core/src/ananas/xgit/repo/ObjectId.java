package ananas.xgit.repo;

public interface ObjectId {

	class Factory {
		public static ObjectId create(String s) {
			return new DefaultObjectId(s);
		}
	}

}
