package test.ananas.xgitlite;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoFinder;

public class TestXGitLite {

	public static void main(String[] arg) {

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

			File wkdir = repo.getWorkingDirectory() ;
			repo. add  ( wkdir  ) ;
			repo. commit   ( wkdir  ) ;
			
			System.out.println("init repo at " + repo_dir);

		} catch (XGLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
