package ananas.xgitlite.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Helper {

	public static void saveProperties(Properties prop, File file)
			throws IOException {

		FileOutputStream out = new FileOutputStream(file);
		prop.store(out, file.getName());
		out.close();
	}

}
