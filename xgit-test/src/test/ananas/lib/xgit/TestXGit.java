package test.ananas.lib.xgit;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.util.PropertiesLoader;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitRepoFactory;
import ananas.lib.xgit.task.ext.RepoInit;

public class TestXGit implements Runnable {

	public static void main(String[] arg) {

		(new TestXGit()).run();
		System.out.println("THE END");

	}

	@Override
	public void run() {
		PropertiesLoader.Util.loadPropertiesToSystem(this, "system.properties");
		this.__unsafe_run();
	}

	private void __unsafe_run() {

		String path = "/home/xukun/snowflake_VMs/the1st.xgit";

		VFileSystem vfs = VFS.getDefaultFactory().defaultFileSystem();
		VFile file = vfs.newFile(path);

		XGitRepoFactory fact = XGitRepoFactory.Util.getDefaultFactory();
		XGitRepo repo = fact.createRepo(file);

		RepoInit init = repo.getTaskFactory().doInit(repo);
		init.getTaskContext().start();

		repo.getTaskFactory().doOpen(repo).getTaskContext().start();

	}

}
