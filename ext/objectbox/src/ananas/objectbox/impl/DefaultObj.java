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
import ananas.objectbox.IObjectCtrl;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;

public class DefaultObj implements IObject {

	private final ObjectId _id;
	private final IBox _box;
	private final LocalObject _go;
	private final VFile _head_file;
	private final VFile _body_file;

	private IObjectCtrl _ctrl;
	private Class<?> _ctrl_class;
	private Map<String, String> _head;
	private List<String> _headers;

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

		if (this._head_file != null)
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
			this._head = map;
			this._headers = new ArrayList<String>(map.keySet());

			// class & object
			String clsName = map.get(HeadKey.ob_class);
			Class<?> cls = Class.forName(clsName);
			this._ctrl_class = cls;
			IObjectCtrl ctrl = (IObjectCtrl) cls.newInstance();
			// body_obj.bindHead(this);
			this._ctrl = ctrl;

			// load header
			ctrl.onLoad(this);

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
		return this._head.get(key);
	}

	@Override
	public String[] listHeaders() {
		__init();
		List<String> list = this._headers;
		return list.toArray(new String[list.size()]);
	}

	@Override
	public Class<?> getControllerClass() {
		__init();
		return this._ctrl_class;
	}

	@Override
	public IObjectCtrl getController() {
		__init();
		return this._ctrl;
	}

}
