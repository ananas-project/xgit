package test.ananas.lib.xgit;

import java.io.File;
import java.net.URL;

import ananas.lib.xgit.XGitRepository;
import ananas.lib.xgit.XGitRepositoryFactory;

public class TestXGit implements Runnable {

	public static void main(String[] arg) {
		(new TestXGit()).run();
	}

	@Override
	public void run() {

		File dir = this.getReposDir();

		XGitRepositoryFactory factory = XGitRepository.Factory.getFactory();

		XGitRepository repos0 = factory.createNewRepository(dir);
		repos0.getObjectsManager();

		XGitRepository repos1 = factory.openRepository(dir);
		repos1.getObjectsManager();

	}

	private File getReposDir() {
		URL url = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		File file = new File(url.getPath());
		return new File(file.getParentFile(), "repos/.git");
	}

}
