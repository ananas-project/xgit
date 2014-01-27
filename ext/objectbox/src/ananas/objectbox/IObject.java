package ananas.objectbox;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IObject {

	interface HeadKey {

		String create_time = "Create-Time";
		String ob_class = "Content-Type";

	}

	IBox getOwnerBox();

	ObjectId getId();

	String getHeader(String key);

	String[] getHeaderNames();

	String getType();

	Class<?> getTypeClass();

	VFile getHeadFile();

	VFile getBodyDirectory();

	VFile getBodyFile();

	ObjectId[] listLinks();

	void removeLink(ObjectId link);

	void addLink(ObjectId link);

}
