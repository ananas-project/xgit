package ananas.lib.xgit;

import java.io.File;

public interface RepositoryFactory {

	Repository openRepository(File file, boolean bare) throws XGitException;

	Repository createNewRepository(File file, boolean bare)
			throws XGitException;

}
