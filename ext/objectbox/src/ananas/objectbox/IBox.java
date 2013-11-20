package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IBox {

	IObject getObject(ObjectId id);

	IObject newObject(String type, Map<String, String> headers);

	VFile getBaseDirectory();
}
