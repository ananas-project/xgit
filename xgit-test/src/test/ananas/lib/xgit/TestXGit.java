package test.ananas.lib.xgit;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitRepoFactory;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.util.DefaultXGitRepoFactory;

public class TestXGit implements Runnable {

	public static void main(String[] arg) {

		(new TestXGit()).run();
		System.out.println("THE END");

	}

	@Override
	public void run() {

		this.__unsafe_run();

	}

	private void __unsafe_run() {

		String path = "/home/xukun/snowflake_VMs/the1st.xgit";

		VFileSystem vfs = VFS.getFactory().defaultFileSystem();
		VFile file = vfs.newFile(path);

		XGitRepoFactory fact = new DefaultXGitRepoFactory();
		XGitRepo repo = fact.createRepo(file);

		RepoInit init = repo.getTaskFactory().doInit(repo);
		init.getTaskContext().start();

	}

}
