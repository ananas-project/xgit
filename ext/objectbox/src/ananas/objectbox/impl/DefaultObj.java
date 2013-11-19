package ananas.objectbox.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.objectbox.IBox;
import ananas.objectbox.IObjectBody;
import ananas.objectbox.IObjectHead;
import ananas.objectbox.IObjectIOManager;
import ananas.objectbox.IObjectLoader;
import ananas.objectbox.IObjectSaver;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;

public class DefaultObj implements IObjectHead {

	private final ObjectId _id;
	private final IBox _box;
	private final LocalObject _go;

	private VFile _head_file;
	private VFile _body_file;
	private IObjectSaver _saver;
	private IObjectLoader _loader;
	private IObjectBody _body_object;
	private Class<?> _body_class;
	private long _create_time;
	private Map<String, String> _head;
	private boolean _is_mod;

	public DefaultObj(IBox box, LocalObject go) {
		this._box = box;
		this._id = go.id();
		this._go = go;
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

			// time
			this._create_time = Long.parseLong(map.get(HeadKey.create_time));

			// class & object
			String clsName = map.get(HeadKey.ob_class);
			Class<?> cls = Class.forName(clsName);
			this._body_class = cls;
			IObjectBody body_obj = (IObjectBody) cls.newInstance();
			body_obj.bindHead(this);
			this._body_object = body_obj;

			// io
			IObjectIOManager iom = this._box.getObjectIOManager();
			IObjectLoader loader = iom.getLoader(cls);
			IObjectSaver saver = iom.getSaver(cls);
			this._loader = loader;
			this._saver = saver;

			// file
			VFile headfile, bodyfile;
			headfile = go.getZipFile();
			VFileSystem vfs = headfile.getVFS();
			String extName = saver.getExtName();
			String bodyName = headfile.getName() + "." + extName;
			bodyfile = vfs.newFile(headfile.getParentFile(), bodyName);
			this._head_file = headfile;
			this._body_file = bodyfile;

			this.load();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Map<String, String> getFields() {
		__init();
		return new HashMap<String, String>(this._head);
	}

	@Override
	public long getCreateTime() {
		__init();
		return this._create_time;
	}

	@Override
	public IObjectBody getBody() {
		__init();
		return this._body_object;
	}

	@Override
	public void load() {
		__init();
		IObjectHead obj = this;
		this.getLoader().load(obj);
	}

	@Override
	public void save() {
		__init();
		IObjectHead obj = this;
		this.getSaver().save(obj);
	}

	@Override
	public IObjectLoader getLoader() {
		__init();
		return this._loader;
	}

	@Override
	public IObjectSaver getSaver() {
		__init();
		return this._saver;
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
	public void setModified(boolean isMod) {
		this._is_mod = isMod;
	}

	@Override
	public boolean isModified() {
		return this._is_mod;
	}

	@Override
	public Class<?> getBodyClass() {
		__init();
		return this._body_class;
	}

}
