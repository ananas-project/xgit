package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface RepositoryFactory {

	Repository openRepository(VFile file, boolean bare, XGitEnvironment envi)
			throws XGitException;

	Repository createNewRepository(VFile file, boolean bare,
			XGitEnvironment envi) throws XGitException;

}
