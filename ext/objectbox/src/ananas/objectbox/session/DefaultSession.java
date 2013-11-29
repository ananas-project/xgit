package ananas.objectbox.session;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.objectbox.DefaultBox;
import ananas.objectbox.IBox;
import ananas.objectbox.IObject;
import ananas.xgit.repo.ObjectId;

public class DefaultSession implements ISession {

	private final IBox _box;
	private final Map<ObjectId, IElement> _cache = new Hashtable<ObjectId, IElement>();

	public DefaultSession(VFile dir) {
		this._box = new DefaultBox(dir);
	}

	public DefaultSession(IBox box) {
		this._box = box;
	}

	public DefaultSession(DefaultSession session) {
		this._box = session._box;
	}

	@Override
	public IElement getElement(ObjectId id) {
		if (id == null)
			return null;
		IElement element = _cache.get(id);
		if (element != null)
			return element;
		IObject obj = _box.getObject(id);
		if (obj == null)
			return null;
		element = new ElementImpl(this, obj);
		_cache.put(id, element);
		return element;
	}

	@Override
	public IElement createElement(Class<?> wrapperClass,
			Map<String, String> header) {
		IObject obj = _box.newObject(wrapperClass, header);
		ObjectId id = obj.getId();
		return this.getElement(id);
	}

	@Override
	public ISession newSession() {
		return new DefaultSession(_box);
	}

	@Override
	public void save() {
		Collection<IElement> list = _cache.values();
		for (IElement el : list) {
			if (el.isModified()) {
				System.out.println("save body : " + el.getBodyFile());
				el.save();
			}
		}
	}

}
