package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IBox {

	IObjectCtrl getObject(ObjectId id);

	IObjectCtrl newObject(Class<?> ctrlClass, Map<String, String> head);

	VFile getBaseDirectory();
}
