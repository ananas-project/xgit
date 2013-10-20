package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface ExtIndexInfo {

	VFile getTargetFile();

	ObjectId getObjectId();

	void add() throws IOException;

	void remove();

	void update();

}
