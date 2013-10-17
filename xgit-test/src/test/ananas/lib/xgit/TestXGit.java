package test.ananas.lib.xgit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.util.PropertiesLoader;
import ananas.xgit.boot.DefaultXGitBootstrap;
import ananas.xgit.repo.local.DefaultLocalRepoFactory;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;

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

		(new DefaultXGitBootstrap()).boot();

		LocalRepo repo = (new DefaultLocalRepoFactory()).createRepo(file);
		LocalObjectBank bank = repo.getObjectBank();

		byte[] ba = "hello, world".getBytes();
		InputStream in = new ByteArrayInputStream(ba);

		try {
			LocalObject go = bank.addObject("blob", ba.length, in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
