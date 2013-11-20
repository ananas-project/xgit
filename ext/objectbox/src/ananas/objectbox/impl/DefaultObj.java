package ananas.objectbox.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.objectbox.IBox;
import ananas.objectbox.IObject;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;

public class DefaultObj implements IObject {

	private final ObjectId _id;
	private final IBox _box;
	private final LocalObject _go;
	private final VFile _head_file;
	private final VFile _body_file;

	private String _obj_class;
	private Map<String, String> _header_map;
	private List<String> _header_names;

	public DefaultObj(IBox box, LocalObject go) {
		this._box = box;
		this._id = go.id();
		this._go = go;

		VFile hfile = go.getZipFile();
		VFileSystem vfs = hfile.getVFS();
		this._head_file = hfile;
		this._body_file = vfs.newFile(hfile.getParentFile(), hfile.getName()
				+ ".body");
	}

	@Override
	public IBox getOwnerBox() {
		return this._box;
	}

	@Override
	public ObjectId getId() {
		return this._id;
	}

	private void __init() {

		if (this._obj_class != null)
			return;

		try {
			// head
			LocalObject go = this._go;
			InputStream in = go.openRawInputStream();
			Properties prop = new Properties();
			prop.load(in);
			in.close();
			Map<String, String> map = new HashMap<String, String>();
			for (Object k : prop.keySet()) {
				String key = k.toString();
				String val = prop.getProperty(key);
				map.put(key, val);
			}
			this._header_map = map;
			this._header_names = new ArrayList<String>(map.keySet());

			// class & object
			this._obj_class = map.get(HeadKey.ob_class);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public VFile getHeadFile() {
		__init();
		return this._head_file;
	}

	@Override
	public VFile getBodyFile() {
		__init();
		return this._body_file;
	}

	@Override
	public String getHeader(String key) {
		__init();
		return this._header_map.get(key);
	}

	@Override
	public String[] getHeaderNames() {
		__init();
		List<String> list = this._header_names;
		return list.toArray(new String[list.size()]);
	}

	@Override
	public String getType() {
		__init();
		return this._obj_class;
	}

}
