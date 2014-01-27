package ananas.objectbox.session;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public abstract class JsonElementBase extends SessionWrapper {

	public JsonElementBase() {
	}

	protected abstract void onLoad(JSONObject json, Map<String, String> header);

	protected abstract void onSave(JSONObject json);

	@Override
	public final void onLoad(IElement element) {
		super.onLoad(element);

		Map<String, String> map = new HashMap<String, String>();
		String[] keys = element.getHeaderNames();
		for (String key : keys) {
			String value = element.getHeader(key);
			map.put(key, value);
		}
		JSONObject json = null;
		try {
			VFile file = element.getBodyFile();
			if (file.exists()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream in = new VFileInputStream(file);
				byte[] buf = new byte[1024];
				for (;;) {
					int cb = in.read(buf);
					if (cb < 0)
						break;
					baos.write(buf, 0, cb);
				}
				in.close();
				json = (JSONObject) JSON.parse(baos.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json == null) {
			json = new JSONObject();
		}
		this.onLoad(json, map);
	}

	@Override
	public final void onSave(IElement element) {
		super.onSave(element);
		IElement tar = this.getTarget();
		if (!tar.equals(element)) {
			return;
		}

		VFile file = element.getBodyFile();
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}

		try {
			JSONObject json = new JSONObject();
			this.onSave(json);
			byte[] ba = JSON.toJSONBytes(json);

			OutputStream out = new VFileOutputStream(file);
			out.write(ba);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
