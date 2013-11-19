package ananas.objectbox;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IObject {

	interface HeadKey {

		String create_time = "Create-Time";
		String ob_class = "Class";

	}

	IBox getOwnerBox();

	ObjectId getId();

	String getHeader(String key);

	String[] listHeaders();

	Class<?> getControllerClass();

	IObjectCtrl getController();

	VFile getHeadFile();

	VFile getBodyFile();

}
