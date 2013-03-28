package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryFactory;
import ananas.lib.xgit.XGitEnvironment;
import ananas.lib.xgit.XGitException;

public class RespositoryFactoryImpl implements RepositoryFactory {

	@Override
	public Repository openRepository(final VFile file, boolean bare,
			XGitEnvironment envi) throws XGitException {
		Repository repos = this._newReposForOpen(file, bare, envi);
		if (!repos.check()) {
			throw new XGitException("Check the repos failed.");
		}
		return repos;
	}

	private Repository _newReposForOpen(final VFile file, boolean bare,
			XGitEnvironment profile) {

		final String strDotGit = profile.getRepositoryDirectoryName();
		if (bare) {
			return new RepositoryImpl(file, bare, profile);
		} else {
			// seek as normal repos for .git
			VFileSystem vfs = file.getVFS();
			for (VFile file2 = file; file2 != null; file2 = file2
					.getParentFile()) {
				VFile dotGit = vfs.newFile(file2, strDotGit);
				if (dotGit.exists() && dotGit.isDirectory()) {
					return new RepositoryImpl(dotGit, bare, profile);
				}
			}
			// or, open as bare

			throw new XGitException("Cannot find a repos in the path:" + file);
		}
	}

	@Override
	public Repository createNewRepository(VFile file, boolean bare,
			XGitEnvironment envi) throws XGitException {

		final String dotGit = envi.getRepositoryDirectoryName();
		VFileSystem vfs = file.getVFS();

		if (bare) {
			if (file.exists()) {
				throw new XGitException("The directory " + file
						+ " has existed.");
			}
		} else {
			if (file.getName().equals(dotGit)) {
				file = file.getParentFile();
			}
			if (file.exists()) {
				throw new XGitException("The directory " + file
						+ " has existed.");
			}
			file = vfs.newFile(file, dotGit);
		}

		Repository repos = new RepositoryImpl(file, bare, envi);
		if (!repos.init()) {
			throw new XGitException("Cannot init the repos.");
		}
		return repos;
	}

}
