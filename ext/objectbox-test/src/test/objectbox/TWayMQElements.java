package test.objectbox;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.util.PropertiesLoader;
import ananas.objectbox.DefaultBox;
import ananas.objectbox.IBox;
import ananas.objectbox.ITypeRegistrar;
import ananas.objectbox.session.DefaultSession;
import ananas.objectbox.session.ISession;
import ananas.waymq.element.Group;
import ananas.waymq.element.Phone;
import ananas.waymq.element.User;
import ananas.xgit.boot.DefaultXGitBootstrap;
import ananas.xgit.repo.ObjectId;

public class TWayMQElements {

	@Test
	public void test() {

		ISession session = this.__init();

		String num = "13012345678";
		Phone phone = Phone.newInstance(session, num);
		ObjectId id = phone.getTarget().getId();
		System.out.println(phone.getClass() + "::" + id + "::" + num);

		User user = phone.getUser(true);
		String name = user.getName();
		if (name == null) {
			user.setName("Mike");
		}
		id = user.getTarget().getId();
		System.out.println(user.getClass() + "::" + id + "::" + user.getName());

		Group[] glist = user.listHoldingGroups();
		if (glist.length < 1) {
			Group group = Group.newInstance(user);
			System.out.println("new " + group);
		}

		session.save();
	}

	private void _regTypes(IBox box) {
		Properties prop = PropertiesLoader.Util.loaderFor(this,
				"type-reg.properties").load();
		ITypeRegistrar reg = box.getTypeRegistrar();
		ITypeRegistrar.Util.loadTypes(reg, prop);
	}

	private ISession __init() {

		String name = "boot.properties";
		ananas.lib.util.PropertiesLoader.Util
				.loadPropertiesToSystem(this, name);

		(new DefaultXGitBootstrap()).boot();

		File file0 = this.getTestRepo();
		String path = file0.getAbsolutePath();

		VFile file = VFS.getDefaultFactory().defaultFileSystem().newFile(path);
		System.out.println("test/repo at " + file);
		IBox box = new DefaultBox(file);

		this._regTypes(box);

		return new DefaultSession(box);

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
		(new TWayMQElements()).test();
	}

}
