package ananas.xgit.repo.local;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface ExtIndexInfo {

	VFile getTargetFile();

	ObjectId getObjectId();

	void add();

	void remove();

	void update();

}
