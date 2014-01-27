package ananas.objectbox.session;

import ananas.lib.io.vfs.VFile;
import ananas.objectbox.IObject;
import ananas.xgit.repo.ObjectId;

class ElementImpl implements IElement {

	private ISession _session;
	private IObject _obj;
	private IWrapper _wrapper;
	private boolean _is_mod;

	public ElementImpl(ISession session, IObject obj) {
		this._session = session;
		this._obj = obj;
	}

	@Override
	public ISession getSession() {
		return this._session;
	}

	@Override
	public ObjectId getId() {
		return this._obj.getId();
	}

	@Override
	public Class<?> getWrapperClass() {
		return this._obj.getTypeClass();
	}

	@Override
	public IWrapper getWrapper() {
		IWrapper wrapper = this._wrapper;
		if (wrapper != null)
			return wrapper;
		Class<?> cls = this.getWrapperClass();
		try {
			wrapper = (IWrapper) cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		wrapper.onLoad(this);
		this._wrapper = wrapper;
		return wrapper;
	}

	@Override
	public boolean isModified() {
		return this._is_mod;
	}

	@Override
	public void setModified(boolean isMod) {
		this._is_mod = isMod;
	}

	@Override
	public void save() {
		if (this._is_mod) {
			this._is_mod = false;
		} else {
			return;
		}
		this.getWrapper().onSave(this);
	}

	@Override
	public void load() {
		this.getWrapper().onLoad(this);
		this._is_mod = false;
	}

	@Override
	public VFile getBodyFile() {
		return this._obj.getBodyFile();
	}

	@Override
	public String[] getHeaderNames() {
		return this._obj.getHeaderNames();
	}

	@Override
	public String getHeader(String name) {
		return this._obj.getHeader(name);
	}

}
