package ananas.impl.xgit.local.indexer.def;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ananas.impl.xgit.util.Hasher;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class ExtIndexNodeImpl implements IndexNode {

	private final VFile _target_file;
	private final Indexer _indexer;
	private final String _off_path;
	private Meta _meta;

	public ExtIndexNodeImpl(Indexer indexer, VFile target, String offsetPath) {
		this._indexer = indexer;
		this._target_file = target;
		this._off_path = offsetPath;
	}

	@Override
	public VFile getTargetFile() {
		return this._target_file;
	}

	@Override
	public ObjectId getObjectId() {
		return this._meta.id();
	}

	private Meta __get_meta() {
		Meta meta = this._meta;
		if (meta != null)
			return meta;
		VFile mf = null;
		{
			VFileSystem vfs = this._target_file.getVFS();
			final int i1, i2;
			i1 = 3;
			i2 = 6;
			final String sha1 = Hasher.stringBySHA1(this._off_path);
			final String p1, p2, p3, sp;
			sp = vfs.separator();
			p1 = sha1.substring(0, i1);
			p2 = sha1.substring(i1, i2);
			p3 = sha1.substring(i2);
			String name = p1 + sp + p2 + sp + p3;
			mf = vfs.newFile(this._indexer.getIndexDB(), name);
		}
		meta = new Meta(mf, this._off_path);

		try {
			meta.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return meta;
	}

	interface Key {

		String path = "path";
		String id = "id";
		String last_mod = "last-modified";
		String length = "length";
		String type = "type";
	}

	class PropGet {

		private final Properties prop;

		public PropGet(Properties _prop) {
			this.prop = _prop;
		}

		public long getLong(String key) {
			String v = prop.getProperty(key);
			if (v == null) {
				return 0;
			} else {
				return Long.parseLong(v);
			}
		}

		public ObjectId getObjectId(String key) {
			String str = null;
			str = prop.getProperty(key);
			if (str == null) {
				// ... 0123456789012345678901234567890123456789
				str = "0000000000000000000000000000000000000000";
			}
			return ObjectId.Factory.create(str);
		}

		public String getString(String key) {
			String v = prop.getProperty(key);
			return ((v == null) ? "" : v);
		}
	}

	class Meta {

		private final String _offset;
		private final VFile _meta_file;

		private long _last_modified;
		private long _length;
		private ObjectId _hash_id;
		private String _type;

		public Meta(VFile file, String offset) {
			this._meta_file = file;
			this._offset = offset;
		}

		public ObjectId id() {
			return this._hash_id;
		}

		public long length() {
			return this._length;
		}

		public void save() throws IOException {

			VFile file = this._meta_file;
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			VFileOutputStream out = new VFileOutputStream(file);

			Properties prop = new Properties();

			prop.setProperty(Key.id, "" + this._hash_id);
			prop.setProperty(Key.path, "" + this._offset);
			prop.setProperty(Key.type, "" + this._type);
			prop.setProperty(Key.length, "" + this._length);
			prop.setProperty(Key.last_mod, "" + this._last_modified);

			prop.store(out, file.getName());

			out.close();
		}

		public void load() throws IOException {

			VFile file = this._meta_file;
			if (!file.exists()) {
				return;
			}

			VFileInputStream in = new VFileInputStream(file);

			Properties prop = new Properties();
			prop.load(in);
			in.close();

			PropGet pget = new PropGet(prop);

			this._length = pget.getLong(Key.length);
			this._hash_id = pget.getObjectId(Key.id);
			this._last_modified = pget.getLong(Key.last_mod);
			this._type = pget.getString(Key.type);
		}

		public void update(long lastModif, ObjectId id, long length) {
			this._length = length;
			this._last_modified = lastModif;
			this._hash_id = id;
		}

		public long lastModified() {
			return this._last_modified;
		}

		public String type() {
			return this._type;
		}

		public boolean exists() {
			return this._meta_file.exists();
		}

		public void setLength(long cb) {
			this._length = cb;
		}

		public void setType(String type) {
			this._type = type;
		}

		public void setLastModified(long time) {
			this._last_modified = time;
		}

		public void delete() {
			this._meta_file.delete();
		}

		public void setObjectId(ObjectId id) {
			this._hash_id = id;
		}
	}

	@Override
	public Indexer getIndexer() {
		return this._indexer;
	}

	@Override
	public void add() throws IOException {
		VFile tar = this._target_file;
		Meta meta = this.__get_meta();
		if (!this.__is_modify(meta, tar)) {
			return;
		}
		if (!tar.exists()) {
			meta.delete();
			return;
		}

		System.out.println("add " + this._off_path);

		if (tar.isDirectory()) {

			meta.setLength(0);
			meta.setType("tree");
			meta.setLastModified(tar.lastModified());
			meta.setObjectId(null);

		} else {
			String type = "blob";
			long length = tar.length();
			LocalObjectBank ob = this.getIndexer().getRepo().getObjectBank();

			InputStream in = new VFileInputStream(tar);
			LocalObject go = ob.addObject(type, length, in);
			in.close();

			meta.setObjectId(go.id());
			meta.setLength(length);
			meta.setType(type);
			meta.setLastModified(tar.lastModified());
		}
		meta.save();
	}

	private boolean __is_modify(Meta meta, VFile tar) {
		if (meta.exists() != tar.exists()) {
			return true;
		}
		if (meta.lastModified() != tar.lastModified()) {
			return true;
		}
		if (tar.isDirectory()) {
			if (!"tree".equals(meta.type())) {
				return true;
			}
		} else {
			if (!"blob".equals(meta.type())) {
				return true;
			}
			if (meta.length() != tar.length()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getType() {
		Meta meta = this.__get_meta();
		return meta.type();
	}

	@Override
	public long getLength() {
		Meta meta = this.__get_meta();
		return meta.length();
	}

	@Override
	public long lastModified() {
		Meta meta = this.__get_meta();
		return meta.lastModified();
	}

}
