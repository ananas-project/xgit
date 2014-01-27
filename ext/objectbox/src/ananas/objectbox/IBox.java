package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IBox {

	ITypeRegistrar getTypeRegistrar();

	IObject getObject(ObjectId id);

	IObject newObject(Class<?> cls, Map<String, String> headers);

	VFile getBaseDirectory();
}
