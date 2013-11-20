package ananas.objectbox.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.objectbox.IObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public final class JsonLS {

	public static JSONObject load(IObject obj) {
		try {
			VFile file = obj.getBodyFile();
			if (!file.exists()) {
				return null;
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
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void save(IObject obj, JSONObject json) {
		try {
			VFile file = obj.getBodyFile();
			byte[] ba = JSON.toJSONBytes(json);
			OutputStream out = new VFileOutputStream(file);
			out.write(ba);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
