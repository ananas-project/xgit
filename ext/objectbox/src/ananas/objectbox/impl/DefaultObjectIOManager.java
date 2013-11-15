package ananas.objectbox.impl;

import ananas.objectbox.IObjectIOManager;
import ananas.objectbox.IObjectLoader;
import ananas.objectbox.IObjectSaver;

public class DefaultObjectIOManager implements IObjectIOManager {

	private IObjectSaver _def_saver;
	private IObjectLoader _def_loader;

	public DefaultObjectIOManager() {
		this._def_loader = new DefaultLoader();
		this._def_saver = new DefaultSaver();
	}

	@Override
	public IObjectLoader getLoader(Class<?> cls) {
		return this._def_loader;
	}

	@Override
	public IObjectSaver getSaver(Class<?> cls) {
		return this._def_saver;
	}

	@Override
	public IObjectLoader registerLoader(Class<?> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectSaver registerSaver(Class<?> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}
