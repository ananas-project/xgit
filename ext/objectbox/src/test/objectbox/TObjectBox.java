package test.objectbox;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.objectbox.DefaultBox;
import ananas.objectbox.IBox;
import ananas.objectbox.IObject;
import ananas.xgit.boot.DefaultXGitBootstrap;

public class TObjectBox {

	@Test
	public void test() {

		this.__init();

		File file0 = this.getTestRepo();
		String path = file0.getAbsolutePath();

		VFile file = VFS.getDefaultFactory().defaultFileSystem().newFile(path);
		System.out.println("test/repo at " + file);
		IBox box = new DefaultBox(file);

		IObject obj = box.newObject(this.getClass(), null);
		System.out.println("body = " + obj.getBodyFile());

	}

	private void __init() {

		String name = this.getClass().getSimpleName() + ".properties";
		ananas.lib.util.PropertiesLoader.Util
				.loadPropertiesToSystem(this, name);

		(new DefaultXGitBootstrap()).boot();

	}

	private File getTestRepo() {

		try {
			URL url = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation();
			File file = new File(url.toURI());
			file = file.getParentFile();

			return new File(file, "test/repo");

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] arg) {

		(new TObjectBox()).test();
	}

}
