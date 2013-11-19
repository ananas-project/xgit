package ananas.objectbox.ctrl.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.objectbox.IObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public abstract class AbsJsonCtrl implements IJsonController {

	@Override
	public void onCreate(IObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(IObject obj) {
		try {
			VFile file = obj.getBodyFile();
			if (!file.exists()) {
				return;
			}
			StringBuilder sb = new StringBuilder();
			InputStream in = new VFileInputStream(file);
			InputStreamReader rdr = new InputStreamReader(in);
			char[] buff = new char[512];
			for (;;) {
				int cc = rdr.read(buff);
				if (cc < 0)
					break;
				sb.append(buff, 0, cc);
			}
			rdr.close();
			in.close();
			JSONObject json = JSON.parseObject(sb.toString());
			this.onLoad(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onSave(IObject obj) {
		try {
			JSONObject json = new JSONObject();
			json = this.onSave(json);
			VFile file = obj.getBodyFile();
			byte[] ba = JSON.toJSONBytes(json);
			OutputStream out = new VFileOutputStream(file);
			out.write(ba);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IObject getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
