package ananas.xgitlite.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import ananas.xgitlite.XGLException;

public class Helper {

	public static void saveProperties(Properties prop, File file)
			throws IOException {

		FileOutputStream out = new FileOutputStream(file);
		prop.store(out, file.getName());
		out.close();
	}

	public static String getOffsetPath(File base, File path)
			throws IOException, XGLException {

		String b = base.getCanonicalPath();
		String p = path.getCanonicalPath();
		if (!p.startsWith(b)) {
			throw new XGLException("the p: [" + p + "] is not a child of b: ["
					+ b + "]");
		}
		if (p.equals(b)) {
			return "";
		} else {
			return p.substring(b.length() + 1);
		}
	}

}
