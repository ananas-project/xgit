package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryFactory;
import ananas.lib.xgit.RepositoryProfile;
import ananas.lib.xgit.XGitException;

public class RespositoryFactoryImpl implements RepositoryFactory {

	private RepositoryProfile mDefaultProfile;

	@Override
	public Repository openRepository(final VFile file, boolean bare,
			RepositoryProfile profile) throws XGitException {
		Repository repos = this._newReposForOpen(file, bare, profile);
		if (!repos.check()) {
			throw new XGitException("Check the repos failed.");
		}
		return repos;
	}

	private Repository _newReposForOpen(final VFile file, boolean bare,
			RepositoryProfile profile) {

		if (profile == null) {
			profile = this.mDefaultProfile;
		}
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
			RepositoryProfile profile) throws XGitException {

		if (profile == null) {
			profile = this.mDefaultProfile;
		}

		final String dotGit = profile.getRepositoryDirectoryName();
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

		Repository repos = new RepositoryImpl(file, bare, profile);
		if (!repos.init()) {
			throw new XGitException("Cannot init the repos.");
		}
		return repos;
	}

	@Override
	public RepositoryProfile getDefaultProfile() {
		return this.mDefaultProfile;
	}

	@Override
	public void setDefaultProfile(RepositoryProfile profile) {
		this.mDefaultProfile = profile;
	}

}
