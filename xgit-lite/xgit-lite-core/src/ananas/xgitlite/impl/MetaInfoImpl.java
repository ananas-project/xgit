package ananas.xgitlite.impl;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ananas.xgitlite.local.MetaInfo;
import ananas.xgitlite.util.Helper;

final class MetaInfoImpl implements MetaInfo {

	private final Properties _prop;
	private final File _file;

	public MetaInfoImpl(File file) {
		this._file = file;
		this._prop = new Properties();
	}

	@Override
	public long getLastModified() {
		return this.getLong(Key.git_last_mod);
	}

	@Override
	public void setLastModified(long lm) {
		this.setLong(Key.git_last_mod, lm);
	}

	@Override
	public String getId() {
		return this.getString(Key.git_id);
	}

	@Override
	public void setId(String id) {
		this.setString(Key.git_id, id);
	}

	@Override
	public boolean load() throws IOException {
		return Helper.loadProperties(_prop, _file);
	}

	@Override
	public void save() throws IOException {
		if (!_file.exists()) {
			_file.getParentFile().mkdirs();
		}
		Helper.saveProperties(_prop, _file);
	}

	@Override
	public Properties getProperties() {
		return this._prop;
	}

	@Override
	public long getLong(String key) {
		String str = _prop.getProperty(key, "0");
		return Long.parseLong(str);
	}

	@Override
	public String getString(String key) {
		String str = _prop.getProperty(key);
		return str;
	}

	@Override
	public void setLong(String key, long value) {
		_prop.setProperty(key, Long.toString(value));
	}

	@Override
	public void setString(String key, String value) {
		if (value == null) {
			_prop.remove(key);
		} else {
			_prop.setProperty(key, value);
		}
	}

	@Override
	public long getLength() {
		return this.getLong(Key.git_length);
	}

	@Override
	public void setLength(long length) {
		this.setLong(Key.git_length, length);
	}

}
