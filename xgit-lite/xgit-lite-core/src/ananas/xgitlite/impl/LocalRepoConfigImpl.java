package ananas.xgitlite.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import ananas.xgitlite.local.LocalRepoConfig;

public class LocalRepoConfigImpl implements LocalRepoConfig {

	private Core _core;

	public LocalRepoConfigImpl() {
		this._core = new MyCore();
	}

	@Override
	public Core getCore() {
		return this._core;
	}

	@Override
	public Remote[] listRemotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Branch[] listBranches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element[] listElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(File file) throws IOException {

		OutputStream out = new FileOutputStream(file);
		out.close();
	}

	@Override
	public void load(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		in.close();
	}

	class MyElement implements Element {

		private final String _type;
		private final String _name;
		private Map<String, String> _kv;

		public MyElement(String type, String name) {
			this._type = type;
			this._name = name;
			this._kv = new Hashtable<String, String>();
		}

		@Override
		public String getType() {
			return this._type;
		}

		@Override
		public String getName() {
			return this._name;
		}

		@Override
		public void set(String key, String value) {
			_kv.put(key, value);
		}

		@Override
		public String get(String key) {
			return _kv.get(key);
		}
	}

	class MyCore extends MyElement implements Core {

		public MyCore() {
			super(Core.type, null);
		}

	}

}
