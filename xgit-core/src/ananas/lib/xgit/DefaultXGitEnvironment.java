package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.task.DefaultTaskFactoryRegistrar;
import ananas.lib.xgit.task.TaskFactoryRegistrar;

public class DefaultXGitEnvironment implements XGitEnvironment {

	protected String repoDirName = ".git";
	private RepositoryFactory mFactory;
	private final TaskFactoryRegistrar mTaskFactoryReg;

	public DefaultXGitEnvironment() {
		this.mTaskFactoryReg = new DefaultTaskFactoryRegistrar();
	}

	@Override
	public String getRepositoryDirectoryName() {
		return this.repoDirName;
	}

	@Override
	public RepositoryFactory getRepositoryFactory() {
		RepositoryFactory factory = this.mFactory;
		if (factory == null) {
			factory = RepositoryFactoryLoader.getFactory();
			this.mFactory = factory;
		}
		return factory;
	}

	@Override
	public Repository openRepository(VFile file, boolean bare)
			throws XGitException {
		return this.getRepositoryFactory().openRepository(file, bare, this);
	}

	@Override
	public Repository createNewRepository(VFile file, boolean bare)
			throws XGitException {
		return this.getRepositoryFactory()
				.createNewRepository(file, bare, this);
	}

	@Override
	public TaskFactoryRegistrar getTaskFactoryRegistrar() {
		return this.mTaskFactoryReg;
	}
}
