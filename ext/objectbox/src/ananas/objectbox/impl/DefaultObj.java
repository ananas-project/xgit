package ananas.objectbox.impl;

import java.io.IOException;
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
	private final VFile _body_dir;

	private String _obj_type;
	private Map<String, String> _header_map;
	private List<String> _header_names;

	public DefaultObj(IBox box, LocalObject go) {

		this._box = box;
		this._id = go.id();
		this._go = go;

		VFile hfile = go.getZipFile();
		VFileSystem vfs = hfile.getVFS();
		this._head_file = hfile;
		VFile dir = vfs.newFile(hfile.getParentFile(), "." + hfile.getName());
		this._body_dir = dir;
		this._body_file = vfs.newFile(dir, "body");
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

		if (this._header_map != null)
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
			this._header_names = new ArrayList<String>(map.keySet());

			// class & object
			this._obj_type = map.get(HeadKey.ob_class);
			this._header_map = map;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public VFile getHeadFile() {
		return this._head_file;
	}

	@Override
	public VFile getBodyFile() {
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
		return this._obj_type;
	}

	@Override
	public Class<?> getTypeClass() {
		__init();
		String type = this._obj_type;
		return this.getOwnerBox().getTypeRegistrar().getClass(type);
	}

	@Override
	public VFile getBodyDirectory() {
		return this._body_dir;
	}

	@Override
	public ObjectId[] listLinks() {

		List<ObjectId> list = new ArrayList<ObjectId>();
		VFile dir = this._body_dir;
		if (dir.exists()) {
			String suffix = ".link";
			String[] names = dir.list();
			for (String name : names) {
				if (name.endsWith(suffix)) {
					String n2 = name.substring(0,
							name.length() - suffix.length());
					if (n2.length() == 40) {
						ObjectId id = ObjectId.Factory.create(n2);
						list.add(id);
					}
				}
			}
		}
		return list.toArray(new ObjectId[list.size()]);
	}

	@Override
	public void removeLink(ObjectId link) {
		VFileSystem vfs = _body_dir.getVFS();
		VFile file = vfs.newFile(_body_dir, link + ".link");
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public void addLink(ObjectId link) {
		VFileSystem vfs = _body_dir.getVFS();
		VFile file = vfs.newFile(_body_dir, link + ".link");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
