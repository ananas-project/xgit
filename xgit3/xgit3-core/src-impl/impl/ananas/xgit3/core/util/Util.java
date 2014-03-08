package impl.ananas.xgit3.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.Properties;

import ananas.xgit3.core.HashID;

public class Util {

	public static Properties loadProperties(File file) {
		try {
			if (file.exists()) {
				Properties prop = new Properties();
				InputStream in = new FileInputStream(file);
				Reader reader = new InputStreamReader(in, "UTF-8");
				prop.load(reader);
				reader.close();
				in.close();
				return prop;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveProperties(File file, Properties prop) {
		try {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			Writer wtr = new OutputStreamWriter(out, "UTF-8");
			prop.store(wtr, file.getName());
			wtr.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String hashToString(String content, String alg) {
		byte[] ba = hashToByteArray(content, alg);
		return HashID.Factory.create(ba).toString();
	}

	public static String hashToString(byte[] content, String alg) {
		byte[] ba = hashToByteArray(content, alg);
		return HashID.Factory.create(ba).toString();
	}

	public static byte[] hashToByteArray(String content, String alg) {
		try {
			final char begin = '[';
			final char end = ']';
			StringBuilder sb = new StringBuilder();
			sb.append(begin);
			sb.append(content);
			sb.append(end);
			byte[] ba = sb.toString().getBytes("UTF-8");
			int from, to;
			from = 0;
			to = ba.length - 1;
			for (; from < to; from++) {
				if (ba[from] == begin) {
					from++;
					break;
				}
			}
			for (; from < to; to--) {
				if (ba[to] == end) {
					break;
				}
			}
			return hashToByteArray(ba, from, to - from, alg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] hashToByteArray(byte[] content, String alg) {
		return hashToByteArray(content, 0, content.length, alg);
	}

	public static byte[] hashToByteArray(byte[] content, int off, int len,
			String alg) {
		try {
			MessageDigest md = MessageDigest.getInstance(alg);
			md.update(content, off, len);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
