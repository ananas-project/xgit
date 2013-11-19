package test.objectbox;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.junit.Test;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.objectbox.DefaultBox;
import ananas.objectbox.IBox;
import ananas.objectbox.IObjectBody;
import ananas.objectbox.IObjectHead;
import ananas.objectbox.IObjectLS;
import ananas.objectbox.body.json.JsonBody;
import ananas.objectbox.body.json.JsonBodyLS;
import ananas.xgit.boot.DefaultXGitBootstrap;
import ananas.xgit.repo.ObjectId;

import com.alibaba.fastjson.JSONObject;

public class TObjectBox extends JsonBody {

	@Test
	public void test() {

		this.__init();

		File file0 = this.getTestRepo();
		String path = file0.getAbsolutePath();

		VFile file = VFS.getDefaultFactory().defaultFileSystem().newFile(path);
		System.out.println("test/repo at " + file);
		IBox box = new DefaultBox(file);

		Class<?> cls = this.getClass();
		IObjectLS ls = new JsonBodyLS();
		box.getObjectIOManager().register(cls, ls);

		IObjectBody body = box.newObject(cls, null);
		IObjectHead head = body.getHead();
		System.out.println("body = " + head.getBodyFile());

		ObjectId id = head.getId();
		body = box.getObject(id);

		Map<String, String> fields = head.getFields();
		System.out.println("head=" + fields);
		System.out.println("body=" + body);
		head.save();
		head.load();

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

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}

}
