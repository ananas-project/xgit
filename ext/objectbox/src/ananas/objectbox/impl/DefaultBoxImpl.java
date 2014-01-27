package ananas.objectbox.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ananas.impl.xgit.local.LocalObjectBankImpl;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.lib.io.vfs.VFileSystem;
import ananas.objectbox.IBox;
import ananas.objectbox.IObject;
import ananas.objectbox.ITypeRegistrar;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class DefaultBoxImpl implements IBox {

	private final VFile _base_dir;
	private final LocalObjectBank _obj_bank;
	private final ITypeRegistrar _type_reg;

	public DefaultBoxImpl(VFile baseDir) {
		this._base_dir = baseDir;
		this._obj_bank = new LocalObjectBankImpl(baseDir);
		this._type_reg = new DefaultTypeReg();
	}

	@Override
	public IObject getObject(ObjectId id) {
		LocalObject inner = _obj_bank.getObject(id);
		if (!inner.exists()) {
			return null;
		}
		return new DefaultObj(this, inner);
	}

	@Override
	public IObject newObject(Class<?> cls, Map<String, String> headers) {
		try {
			boolean showHeadPlain = true;

			byte[] ba = this.__buildHeader(cls, headers);
			InputStream in = new ByteArrayInputStream(ba);
			String type = "objectbox-header";
			LocalObject go = _obj_bank.addObject(type, ba.length, in);

			IObject iobj = new DefaultObj(this, go);
			VFile dir = iobj.getBodyDirectory();
			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (showHeadPlain) {
				VFileSystem vfs = dir.getVFS();
				VFile hf = vfs.newFile(dir, "head");
				VFileOutputStream out = new VFileOutputStream(hf);
				out.write(ba);
				out.close();
			}

			return iobj;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] __buildHeader(Class<?> cls, Map<String, String> headers) {
		try {

			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			String type = this.getTypeRegistrar().getType(cls);
			headers.put(IObject.HeadKey.ob_class, type + "");
			String ctime = headers.get(IObject.HeadKey.create_time);
			if (ctime == null) {
				long now = System.currentTimeMillis();
				headers.put(IObject.HeadKey.create_time, now + "");
			}

			StringBuilder sb = new StringBuilder();
			List<String> keys = new ArrayList<String>(headers.keySet());
			java.util.Collections.sort(keys);
			for (String key : keys) {
				String value = headers.get(key);
				sb.append(key.trim());
				sb.append('=');
				sb.append(value.trim());
				sb.append("\r\n");
			}
			return sb.toString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	@Override
	public VFile getBaseDirectory() {
		return this._base_dir;
	}

	@Override
	public ITypeRegistrar getTypeRegistrar() {
		return this._type_reg;
	}

}
