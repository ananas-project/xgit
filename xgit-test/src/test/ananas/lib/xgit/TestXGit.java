package test.ananas.lib.xgit;

import java.net.URL;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryFactory;

public class TestXGit implements Runnable {

	public static void main(String[] arg) {
		boolean bare;

		bare = true;
		(new TestXGit(bare)).run();

		System.out.println("============================");

		bare = false;
		(new TestXGit(bare)).run();
	}

	private boolean mBare;

	public TestXGit(boolean bare) {
		this.mBare = bare;
	}

	@Override
	public void run() {

		System.out.println(this + ".begin");

		VFile dir = this.getReposDir();
		VFileSystem vfs = dir.getVFS();

		RepositoryFactory factory = Repository.Factory.getFactory();

		boolean bare = this.mBare;

		if (bare) {
			dir = vfs.newFile(dir, "bare/xxx.git");
		} else {
			dir = vfs.newFile(dir, "nobare/yyy");
		}

		try {
			System.out.println("try to create : " + dir);
			Repository repos0 = factory.createNewRepository(dir, bare, null);
			repos0.getObjectsManager();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!bare) {

			dir = vfs.newFile(dir, "723/824");
		}

		System.out.println("try to open : " + dir);
		Repository repos1 = factory.openRepository(dir, bare, null);
		repos1.getObjectsManager();

		System.out.println("repos1 = " + repos1.getFile());

		System.out.println(this + ".end");

	}

	private VFile getReposDir() {

		VFileSystem vfs = VFS.getFactory().createFileSystem(null);

		URL url = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		VFile file = vfs.newFile(url.getPath());
		return vfs.newFile(file.getParentFile(), "repos");
	}

}
