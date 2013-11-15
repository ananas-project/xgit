package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IObject {

	interface HeadKey {

		String create_time = "Create-Time";
		String ob_class = "Class";

	}

	IBox getOwnerBox();

	ObjectId getId();

	Map<String, String> getHead();

	Class<?> getClassOB();

	long getCreateTime();

	Object getBody();

	void load();

	void save();

	void setModified(boolean isMod);

	boolean isModified();

	IObjectLoader getLoader();

	IObjectSaver getSaver();

	VFile getHeadFile();

	VFile getBodyFile();

}
