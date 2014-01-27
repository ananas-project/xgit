package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import ananas.impl.xgit.util.StreamPump;
import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class LocalObjectBankImpl implements LocalObjectBank {

	interface Const {

		int cache_size_max = 512;

	}

	class LObjCache {

		private LObjCache _parent;
		private final Map<ObjectId, LocalObject> _map;

		public LObjCache(LObjCache parent) {
			this._parent = parent;
			this._map = new Hashtable<ObjectId, LocalObject>();
			if (parent != null) {
				parent._parent = null;
			}
		}

		public LocalObject getObject(ObjectId id) {
			LocalObject obj = _map.get(id);
			if (obj == null) {
				LObjCache parent = _parent;
				if (parent == null) {
					return null;
				}
				obj = parent.getObject(id);
				if (obj == null) {
					return null;
				}
				_map.put(obj.id(), obj);
			}
			return obj;
		}

		public boolean isFull() {
			return (this._map.size() > Const.cache_size_max);
		}

		public void put(LocalObject obj) {
			_map.put(obj.id(), obj);
		}
	}

	private final VFile _dir;
	private LObjCache _cache;

	public LocalObjectBankImpl(VFile dir) {
		this._dir = dir;
	}

	@Override
	public LocalObject getObject(ObjectId id) {
		LObjCache cache = this.__get_cache();
		LocalObject obj = cache.getObject(id);
		if (obj == null) {
			obj = new LocalObjectImpl(this, id);
			cache.put(obj);
		}
		return obj;
	}

	private LObjCache __get_cache() {
		LObjCache cache1, cache2;
		cache1 = cache2 = this._cache;
		if (cache2 != null) {
			if (cache2.isFull()) {
				cache2 = null;
			}
		}
		if (cache2 == null) {
			cache2 = new LObjCache(cache1);
			this._cache = cache2;
		}
		return cache2;
	}

	@SuppressWarnings("resource")
	@Override
	public LocalObject addObject(String type, long length, InputStream in)
			throws IOException {

		String head = type + " " + length + "E";
		byte[] ba = head.getBytes();
		ba[ba.length - 1] = 0;

		LocalObjectBuilder builder;
		if (length <= (8 * 1024)) {
			builder = new SmallLocalObjectBuilder(this._dir, type, length);
		} else {
			builder = new LargeLocalObjectBuilder(this._dir, type, length);
		}

		OutputStream out = builder.openOutput(ba);
		StreamPump pump = new StreamPump(in, out);
		pump.run();
		out.close();

		ObjectId id = builder.build();
		LocalObject go = this.getObject(id);
		if (go.exists()) {
			builder.delete();
		} else {
			builder.moveTo(go.getZipFile());
		}
		return go;
	}

	@Override
	public VFile getBankDirectory() {
		return this._dir;
	}

	public String toString() {
		return ("[" + this.getClass().getSimpleName() + " " + this._dir + "]");
	}

}
