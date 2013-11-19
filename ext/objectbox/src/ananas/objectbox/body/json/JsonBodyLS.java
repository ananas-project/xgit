package ananas.objectbox.body.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.objectbox.IObjectHead;
import ananas.objectbox.IObjectLS;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonBodyLS implements IObjectLS {

	@Override
	public String getExtName() {
		return "json";
	}

	@Override
	public void save(IObjectHead obj) {
		try {
			JsonBody body = (JsonBody) obj.getBody();
			JSONObject json = new JSONObject();
			json = body.onSave(json);
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
	public void load(IObjectHead obj) {

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
			JsonBody body = (JsonBody) obj.getBody();
			body.onLoad(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
