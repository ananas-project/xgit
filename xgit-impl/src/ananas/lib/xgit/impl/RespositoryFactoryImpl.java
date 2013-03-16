package ananas.lib.xgit.impl;

import java.io.File;

import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryFactory;
import ananas.lib.xgit.XGitException;

public class RespositoryFactoryImpl implements RepositoryFactory {

	@Override
	public Repository openRepository(final File file, boolean bare)
			throws XGitException {
		Repository repos = this._newReposForOpen(file, bare);
		if (!repos.check()) {
			throw new XGitException("Check the repos failed.");
		}
		return repos;
	}

	private Repository _newReposForOpen(final File file, boolean bare) {

		if (bare) {
			return new RepositoryImpl(file, bare);
		} else {
			// seek as normal repos for .git

			for (File file2 = file; file2 != null; file2 = file2
					.getParentFile()) {
				File dotGit = new File(file2, ".git");
				if (dotGit.exists() && dotGit.isDirectory()) {
					return new RepositoryImpl(dotGit, bare);
				}
			}
			// or, open as bare

			throw new XGitException("Cannot find a repos in the path:" + file);
		}
	}

	@Override
	public Repository createNewRepository(File file, boolean bare)
			throws XGitException {

		if (bare) {
			if (file.exists()) {
				throw new XGitException("The directory " + file
						+ " has existed.");
			}
		} else {
			if (file.getName().equals(".git")) {
				file = file.getParentFile();
			}
			if (file.exists()) {
				throw new XGitException("The directory " + file
						+ " has existed.");
			}
			file = new File(file, ".git");
		}

		Repository repos = new RepositoryImpl(file, bare);
		if (!repos.init()) {
			throw new XGitException("Cannot init the repos.");
		}
		return repos;
	}

}
