package ananas.objectbox;

import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.objectbox.impl.DefaultBoxImpl;
import ananas.xgit.repo.ObjectId;

public class DefaultBox implements IBox {

	private final IBox impl;

	public DefaultBox(VFile baseDir) {
		impl = new DefaultBoxImpl(baseDir);
	}

	public ITypeRegistrar getTypeRegistrar() {
		return impl.getTypeRegistrar();
	}

	public IObject getObject(ObjectId id) {
		return impl.getObject(id);
	}

	public IObject newObject(Class<?> cls, Map<String, String> headers) {
		return impl.newObject(cls, headers);
	}

	public VFile getBaseDirectory() {
		return impl.getBaseDirectory();
	}

}
