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
	public IObjectCtrl getObject(ObjectId id) {
		return impl.getObject(id);
	}

	@Override
	public IObjectCtrl newObject(Class<?> cls, Map<String, String> head) {
		return impl.newObject(cls, head);
	}

	@Override
	public VFile getBaseDirectory() {
		return impl.getBaseDirectory();
	}
}
