package test.ananas.lib.xgit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ananas.impl.xgit.util.StreamPump;
import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.util.PropertiesLoader;
import ananas.xgit.XGitException;
import ananas.xgit.boot.DefaultXGitBootstrap;
import ananas.xgit.repo.local.DefaultLocalRepoFactory;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.WorkingDirectory;

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

		try {

			String path = "/home/xukun/snowflake_VMs/the1st/.git";
			// path = "/home/xukun/git/xgit/.git";

			VFileSystem vfs = VFS.getDefaultFactory().defaultFileSystem();
			VFile file = vfs.newFile(path);

			(new DefaultXGitBootstrap()).boot();

			LocalRepo repo;
			if (file.exists()) {
				repo = (new DefaultLocalRepoFactory()).openRepo(file);
			} else {
				repo = (new DefaultLocalRepoFactory()).initRepo(file, false);
			}

			LocalObjectBank bank = repo.getObjectBank();

			String[] list = { "hello1.txt", "hello2.txt" };
			for (String filename : list) {

				InputStream in = this.getClass().getResourceAsStream(filename);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				(new StreamPump(in, baos)).run();
				byte[] ba = baos.toByteArray();

				in = new ByteArrayInputStream(ba);
				LocalObject go = bank.addObject("blob", ba.length, in);
				InputStream in2 = go.openRawInputStream();
				// System.out.println("repo co:");
				// (new StreamPump(in2, System.out)).run();
				in2.close();
				System.out.println();
				System.out.println("id     = " + go.id());
				System.out.println("type   = " + go.type());
				System.out.println("length = " + go.length());

			}

			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("do add");

			WorkingDirectory wd = repo.getWorkingDirectory();
			Indexer indexer = repo.getIndexer();
			IndexNode node = indexer.getNode(wd.getDirectory());
			indexer.add(node, true);

			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XGitException e) {
			e.printStackTrace();
		}

	}
}
