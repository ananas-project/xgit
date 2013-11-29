package ananas.objectbox.impl;

import java.util.Hashtable;
import java.util.Map;

import ananas.objectbox.ITypeRegistrar;

public class DefaultTypeReg implements ITypeRegistrar {

	private final Map<String, MyType> _type_map;

	public DefaultTypeReg() {
		this._type_map = new Hashtable<String, MyType>();
	}

	static class MyType {

		private final String _type;
		private final Class<?> _class;

		public MyType(String type, Class<?> cls) {
			this._type = type;
			this._class = cls;
		}

	}

	@Override
	public void register(String type, Class<?> cls) {
		MyType t = new MyType(type, cls);
		this._type_map.put(cls.getName(), t);
		this._type_map.put(type, t);
	}

	@Override
	public void register(String type, String className) {
		try {
			Class<?> cls = Class.forName(className);
			this.register(type, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class<?> getClass(String type) {
		MyType t = this._type_map.get(type);
		if (t == null) {
			System.err.println("warning:no type for name : " + type);
			return null;
		}
		return t._class;
	}

	@Override
	public String getType(Class<?> cls) {
		MyType t = this._type_map.get(cls.getName());
		if (t == null) {
			System.err.println("warning:no type for name : " + cls);
			return null;
		}
		return t._type;
	}

}
