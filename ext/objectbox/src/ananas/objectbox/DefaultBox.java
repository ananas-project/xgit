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
	public VFile getBaseDirectory() {
		return impl.getBaseDirectory();
	}

	@Override
	public IObject getObject(ObjectId id) {
		return impl.getObject(id);
	}

	@Override
	public IObject newObject(String type, Map<String, String> headers) {
		return impl.newObject(type, headers);
	}
}
