package test.ananas.lib.xgit;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.util.SHA1Value;
import ananas.lib.xgit.ObjectsManager;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.XGitEnvironment;
import ananas.lib.xgit.XGitObject;

public class TestObjectManager {

	public static void main(String[] args) {

		VFileSystem vfs = VFS.getFactory().defaultFileSystem();
		VFile file = vfs.newFile("/tmp/test/xgit/.xgit");

		XGitEnvironment envi = Repository.Factory.getEnvironment();
		try {
			Repository repo = envi.createNewRepository(file, false);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		Repository repo = envi.openRepository(file, false);
		ObjectsManager om = repo.getObjectsManager();

		String sha1 = "03ff2aa74e300045a68a04a0040dcf6a294cb697";

		XGitObject obj = om.getObject(new SHA1Value(sha1));
		// obj.openRawStream();
		obj.getType();

	}
}
