package ananas.lib.xgit;

import java.io.File;

public interface XGitRepositoryFactory {

	XGitRepository openRepository(File file) throws XGitException;

	XGitRepository createNewRepository(File file) throws XGitException;

}
