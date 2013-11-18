package ananas.objectbox.impl;

import java.util.Hashtable;
import java.util.Map;

import ananas.objectbox.IObjectIOManager;
import ananas.objectbox.IObjectLS;
import ananas.objectbox.IObjectLoader;
import ananas.objectbox.IObjectSaver;

public class DefaultObjectIOManager implements IObjectIOManager {

	private IObjectSaver _def_saver;
	private IObjectLoader _def_loader;

	private final Map<Class<?>, IObjectLS> _map = new Hashtable<Class<?>, IObjectLS>();

	public DefaultObjectIOManager() {
		this._def_loader = new DefaultLoader();
		this._def_saver = new DefaultSaver();
	}

	@Override
	public IObjectLoader getLoader(Class<?> cls) {
		IObjectLS ls = this._map.get(cls);
		if (ls == null)
			return this._def_loader;
		return ls;
	}

	@Override
	public IObjectSaver getSaver(Class<?> cls) {
		IObjectLS ls = this._map.get(cls);
		if (ls == null)
			return this._def_saver;
		return ls;
	}

	@Override
	public void register(Class<?> cls, IObjectLS ls) {
		this._map.put(cls, ls);
	}

}
