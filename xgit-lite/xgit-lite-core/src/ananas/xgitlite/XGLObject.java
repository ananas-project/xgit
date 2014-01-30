package ananas.xgitlite;

public interface XGLObject {

	interface Type {

		String blob = "blob";
		String commit = "commit";
		String tag = "tag";
		String tree = "tree";

	}

	ObjectId getId();

	String getType();

	long getLength();

}
