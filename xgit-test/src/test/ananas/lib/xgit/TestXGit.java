package test.ananas.lib.xgit;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.util.PropertiesLoader;
import ananas.lib.xgit.repo.local.DefaultLocalRepoFactory;
import ananas.lib.xgit.repo.local.LocalObjectBank;
import ananas.lib.xgit.repo.local.LocalRepo;

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

		LocalRepo repo = (new DefaultLocalRepoFactory()).createRepo(file);
		LocalObjectBank bank = repo.getObjectBank();
		// bank.getObject( id ) ;

	}

}
