package ananas.objectbox.session;

import java.util.Map;

import ananas.xgit.repo.ObjectId;

public class SessionWrapper implements ISession, IWrapper {

	private ISession _session;
	private IElement _element;

	public SessionWrapper() {
	}

	@Override
	public IElement getElement(ObjectId id) {
		return this._session.getElement(id);
	}

	@Override
	public IElement createElement(Class<?> wrapperClass,
			Map<String, String> header) {
		return this._session.createElement(wrapperClass, header);
	}

	@Override
	public ISession newSession() {
		return this._session.newSession();
	}

	@Override
	public void save() {
		this._session.save();
	}

	@Override
	public void onLoad(IElement element) {
		this._session = element.getSession();
		this._element = element;
	}

	@Override
	public void onSave(IElement element) {
	}

	@Override
	public IElement getTarget() {
		return this._element;
	}
}
