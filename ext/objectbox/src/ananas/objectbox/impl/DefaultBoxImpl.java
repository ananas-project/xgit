package ananas.objectbox.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ananas.impl.xgit.local.LocalObjectBankImpl;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.objectbox.IBox;
import ananas.objectbox.IObject;
import ananas.objectbox.IObject.HeadKey;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class DefaultBoxImpl implements IBox {

	private final VFile _base_dir;

	private final LocalObjectBank _obj_bank;
	private ObjCache _cache;

	public DefaultBoxImpl(VFile baseDir) {
		this._base_dir = baseDir;

		this._obj_bank = new LocalObjectBankImpl(baseDir);
	}

	@Override
	public IObject getObject(ObjectId id) {
		ObjCache cache = this.__get_cache();
		IObject obj = cache.get(id);
		if (obj == null) {
			obj = this.__load_obj(id);
			cache.put(obj);
		}
		return obj;
	}

	private IObject __load_obj(ObjectId id) {
		LocalObject go = this._obj_bank.getObject(id);
		if (go.exists()) {
			return new DefaultObj(this, go);
		} else {
			return null;
		}
	}

	private IObject __new_obj(String type0, Map<String, String> head) {

		if (head == null) {
			head = new HashMap<String, String>();
		}
		// head.put(HeadKey.create_time, "" + System.currentTimeMillis());
		head.put(HeadKey.ob_class, "" + type0);

		try {
			byte[] ba = this.__gen_head_data(head);

			String type = "head";
			int length = ba.length;
			InputStream in = new ByteArrayInputStream(ba);
			LocalObject go = this._obj_bank.addObject(type, length, in);
			in.close();

			VFile file = go.getZipFile();
			VFile hfile = file.getVFS().newFile(file.getParentFile(),
					file.getName() + ".head.txt");
			VFileOutputStream out = new VFileOutputStream(hfile);
			out.write(ba);
			out.close();

			return new DefaultObj(this, go);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] __gen_head_data(Map<String, String> map)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		List<String> keys = new ArrayList<String>(map.keySet());
		java.util.Collections.sort(keys);
		for (String key : keys) {
			String val = map.get(key);
			key = key.trim();
			val = val.trim();
			sb.append(key + "=" + val + "\r\n");
		}
		return sb.toString().getBytes("UTF-8");
	}

	private ObjCache __get_cache() {
		ObjCache cache = this._cache;
		if (cache == null) {
			cache = new ObjCache(null);
			this._cache = cache;
		}

		if (cache._count > 128) {
			cache = new ObjCache(cache);
			cache.clean(3);
			this._cache = cache;
		}

		return cache;
	}

	private static class ObjCache {

		private ObjCache _parent;
		private final Map<ObjectId, IObject> _map;
		private int _count;

		public ObjCache(ObjCache parent) {
			this._parent = parent;
			this._map = new Hashtable<ObjectId, IObject>();
		}

		public void put(IObject obj) {
			if (obj == null)
				return;
			ObjectId id = obj.getId();
			this._map.put(id, obj);
			this._count = this._map.size();
		}

		public IObject get(ObjectId id) {
			IObject obj = this._map.get(id);
			if (obj == null) {
				ObjCache p = this._parent;
				if (p != null) {
					obj = p.get(id);
				}
				if (obj != null) {
					this._map.put(id, obj);
				}
			}
			return obj;
		}

		void clean(int depth) {

			if (depth <= 0) {
				this._parent = null;
				return;
			}

			ObjCache p = this._parent;
			if (p != null) {
				p.clean(depth - 1);
			}

		}

	}

	@Override
	public IObject newObject(String type, Map<String, String> head) {
		IObject obj = this.__new_obj(type, head);
		ObjCache cache = this.__get_cache();
		cache.put(obj);
		return obj;
	}

	@Override
	public VFile getBaseDirectory() {
		return this._base_dir;
	}

}
