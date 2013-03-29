package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.task.TaskFactoryRegistrar;

public interface XGitEnvironment {

	String getRepositoryDirectoryName(); // in default, equals to ".git"

	RepositoryFactory getRepositoryFactory();

	Repository openRepository(VFile file, boolean bare) throws XGitException;

	Repository createNewRepository(VFile file, boolean bare)
			throws XGitException;

	TaskFactoryRegistrar getTaskFactoryRegistrar();
}
