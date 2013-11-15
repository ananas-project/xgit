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

	@Override
	public IObject getObject(ObjectId id) {
		return this.getObject(id);
	}

	@Override
	public IObject newObject(Class<?> cls, Map<String, String> head) {
		return impl.newObject(cls, head);
	}

	@Override
	public IObjectIOManager getObjectIOManager() {
		return impl.getObjectIOManager();
	}

	@Override
	public VFile getBaseDirectory() {
		return impl.getBaseDirectory();
	}
}
