package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IObjectHead {

	interface HeadKey {

		String create_time = "Create-Time";
		String ob_class = "Class";

	}

	IBox getOwnerBox();

	ObjectId getId();

	Map<String, String> getFields();

	Class<?> getBodyClass();

	long getCreateTime();

	IObjectBody getBody();

	void load();

	void save();

	void setModified(boolean isMod);

	boolean isModified();

	IObjectLoader getLoader();

	IObjectSaver getSaver();

	VFile getHeadFile();

	VFile getBodyFile();

}
