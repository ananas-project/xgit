package test.ananas.lib.xgit;

import java.io.File;
import java.net.URL;

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

		File dir = this.getReposDir();

		RepositoryFactory factory = Repository.Factory.getFactory();

		boolean bare = this.mBare;

		if (bare) {
			dir = new File(dir, "bare/xxx.git");
		} else {
			dir = new File(dir, "nobare/yyy");
		}

		try {
			System.out.println("try to create : " + dir);
			Repository repos0 = factory.createNewRepository(dir, bare);
			repos0.getObjectsManager();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!bare) {

			dir = new File(dir, "723/824");
		}

		System.out.println("try to open : " + dir);
		Repository repos1 = factory.openRepository(dir, bare);
		repos1.getObjectsManager();

		System.out.println("repos1 = " + repos1.getFile());

		System.out.println(this + ".end");

	}

	private File getReposDir() {
		URL url = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		File file = new File(url.getPath());
		return new File(file.getParentFile(), "repos");
	}

}
