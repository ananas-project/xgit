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
import ananas.objectbox.IObjectCtrl;
import ananas.objectbox.ctrl.json.AbsJsonCtrl;
import ananas.xgit.boot.DefaultXGitBootstrap;
import ananas.xgit.repo.ObjectId;

import com.alibaba.fastjson.JSONObject;

public class TObjectBox extends AbsJsonCtrl {

	@Test
	public void test() {

		this.__init();

		File file0 = this.getTestRepo();
		String path = file0.getAbsolutePath();

		VFile file = VFS.getDefaultFactory().defaultFileSystem().newFile(path);
		System.out.println("test/repo at " + file);
		IBox box = new DefaultBox(file);

		Class<?> cls = this.getClass();

		IObjectCtrl ctrl = box.newObject(cls, null);
		IObject head = ctrl.getObject();
		System.out.println("body = " + head.getBodyFile());

		ObjectId id = head.getId();
		ctrl = box.getObject(id);

		String[] fields = head.listHeaders();
		System.out.println("head=" + fields);
		System.out.println("ctrl=" + ctrl);

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

	@Override
	public void onLoad(JSONObject root) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject onSave(JSONObject root) {
		// TODO Auto-generated method stub
		return root;
	}

}
