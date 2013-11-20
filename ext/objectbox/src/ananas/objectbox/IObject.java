package ananas.objectbox;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IObject {

	interface HeadKey {

		String create_time = "Create-Time";
		String ob_class = "Type";

	}

	IBox getOwnerBox();

	ObjectId getId();

	String getHeader(String key);

	String[] getHeaderNames();

	String getType();

	VFile getHeadFile();

	VFile getBodyFile();

}
