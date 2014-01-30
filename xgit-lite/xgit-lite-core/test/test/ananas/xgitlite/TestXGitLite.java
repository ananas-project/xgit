package test.ananas.xgitlite;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoFinder;

public class TestXGitLite {

	public static void main(String[] arg) {

		TestXGitLite suite = new TestXGitLite();
		suite.testId();

		File path = new File("/home/xukun/test/xgit-repo/.git");

		try {

			XGitLite xgl = XGitLite.getInstance();
			LocalRepo repo = xgl.createLocalRepo(path);

			File repo_dir = repo.getRepoDirectory();
			if (repo_dir.exists()) {
				repo.check();
			} else {
				repo.init(false);
			}

			LocalRepoFinder finder = xgl.getRepoFinder(true);
			LocalRepo[] rlt = finder.find(path, null);
			if (rlt.length > 0)
				repo = rlt[0];

			File wkdir = repo.getWorkingDirectory();
			repo.add(wkdir);
			repo.commit(wkdir);

			System.out.println("init repo at " + repo_dir);

		} catch (XGLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void testId() {

		System.out.println("test ObjectId");

		final int[] nArray = { 0xab, 0x02, 0x69, 0xfd, 0xfc, 0x93, 0xea, 0xf0,
				0x20, 0xb1, 0x11, 0xc5, 0xe5, 0xe0, 0x79, 0xd9, 0x77, 0xe6,
				0x16, 0xaa };
		final byte[] bArray = new byte[nArray.length];
		for (int i = 0; i < nArray.length; i++) {
			int b = nArray[i] & 0x00ff;
			bArray[i] = (byte) b;
			System.out.print(Integer.toHexString(b) + ", ");
		}
		System.out.println();

		final XGitLite xgl = XGitLite.getInstance();
		final ObjectId id0 = xgl.createObjectId(bArray);
		final String s0 = id0.toString();
		final ObjectId id1 = xgl.createObjectId(s0);
		final String s1 = id1.toString();

		System.out.println("s0 = " + s0);
		System.out.println("s1 = " + s1);
		if (s1.equals(s0)) {
			System.out.println("ok!");
		} else {
			System.out.println("error!!!");
		}
		System.out.println();

	}
}
