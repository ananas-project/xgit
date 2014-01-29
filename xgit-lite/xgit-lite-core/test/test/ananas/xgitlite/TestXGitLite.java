package test.ananas.xgitlite;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.LocalRepo;
import ananas.xgitlite.LocalRepoFinder;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGitLite;

public class TestXGitLite {

	public static void main(String[] arg) {

		File path = new File("/home/xukun/test/xgit-repo/.xgit");

		try {

			XGitLite xgl = XGitLite.getInstance();
			LocalRepo repo = xgl.createLocalRepo(path);

			repo.init(false);

			LocalRepoFinder finder = xgl.getRepoFinder(true);
			LocalRepo[] rlt = finder.find(path, null);
			if (rlt.length > 0)
				repo = rlt[0];

			System.out.println("init repo at " + repo.getRepoDirectory());

		} catch (XGLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
