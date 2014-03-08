package impl.ananas.xgit3.core.local;

import impl.ananas.xgit3.core.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.ext.FileTracker;
import ananas.xgit3.core.local.ext.FileTracking;

public class DefaultFileTracker implements FileTracker {

	interface Key {

		String id = "id";
		String path_hash = "target_path_hash";
		String length = "length";
		String lastMod = "last_modified";

	}

	private final File _base;

	public DefaultFileTracker(File base) {
		this._base = base;
	}

	@Override
	public FileTracking getTracking(File file) {
		File prop_file = this.getPropFileForTarget(file);
		Properties prop = null;
		if (prop_file.exists()) {
			prop = Util.loadProperties(prop_file);
		}
		return new MyFileTracking(prop_file, prop, file);
	}

	static class Helper {

		public static long getLong(Properties prop, String key) {
			if (prop == null)
				return 0;
			String val = prop.getProperty(key);
			if (val == null)
				return 0;
			return Long.parseLong(val);
		}

		public static HashID getHashID(Properties prop, String key) {
			if (prop == null)
				return null;
			String val = prop.getProperty(key);
			if (val == null)
				return null;
			return HashID.Factory.create(val);
		}

		public static String getString(Properties prop, String key) {
			if (prop == null)
				return null;
			return prop.getProperty(key);
		}

		public static String hashFilePath(File file) {
			try {
				String s = file.getCanonicalPath();
				s = Util.hashToString(s, "SHA-256");
				return s;
			} catch (IOException e) {
				e.printStackTrace();
				return "00000000";
			}
		}

	}

	private File getPropFileForTarget(File file) {
		int i = 3;
		String id = Helper.hashFilePath(file);
		StringBuilder sb = new StringBuilder();
		sb.append("track_");
		sb.append(id.substring(0, i));
		sb.append(File.separatorChar);
		sb.append(id.substring(i));
		sb.append(".meta");
		return new File(this._base, sb.toString());
	}

	class MyFileTracking implements FileTracking {

		private final File _prop_path;
		private final File _target_path;

		private long _length;
		private long _last_mod;
		private HashID _id;
		private String _path_hash;

		public MyFileTracking(File prop_path, Properties prop, File target_path) {
			this._prop_path = prop_path;
			this._target_path = target_path;
			this._id = Helper.getHashID(prop, Key.id);
			this._path_hash = Helper.hashFilePath(target_path);
			this._length = Helper.getLong(prop, Key.length);
			this._last_mod = Helper.getLong(prop, Key.lastMod);
			String hp = Helper.getString(prop, Key.path_hash);
			if (!this._prop_path.equals(hp)) {
				this._id = null;
			}
		}

		@Override
		public File getPath() {
			return this._target_path;
		}

		@Override
		public long length() {
			return this._length;
		}

		@Override
		public HashID id() {
			return this._id;
		}

		@Override
		public long lastModified() {
			return this._last_mod;
		}

		@Override
		public void doTrack(HashID id, long lastMod, long length) {
			this._id = id;
			this._last_mod = lastMod;
			this._length = length;
			// save
			Properties prop = new Properties();
			if (_id != null)
				prop.setProperty(Key.id, _id.toString());
			prop.setProperty(Key.path_hash, this._path_hash);
			prop.setProperty(Key.lastMod, Long.toString(this._last_mod));
			prop.setProperty(Key.length, Long.toString(this._length));
			Util.saveProperties(this._prop_path, prop);
		}

		@Override
		public boolean isTracked() {
			File tar = this._target_path;
			if (!tar.exists())
				return false;
			if (tar.isDirectory())
				return false;
			if (this._id == null)
				return false;
			if (tar.lastModified() != this._last_mod)
				return false;
			if (tar.length() != this._length)
				return false;
			return true;
		}
	}

}
