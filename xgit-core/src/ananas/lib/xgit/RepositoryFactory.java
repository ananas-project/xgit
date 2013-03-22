package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface RepositoryFactory {

	RepositoryProfile getDefaultProfile();

	void setDefaultProfile(RepositoryProfile profile);

	Repository openRepository(VFile file, boolean bare,
			RepositoryProfile profile) throws XGitException;

	Repository createNewRepository(VFile file, boolean bare,
			RepositoryProfile profile) throws XGitException;

}
