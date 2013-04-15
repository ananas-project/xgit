package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public class DefaultXGitEnvironment implements XGitEnvironment {

	private RepositoryFactory mFactory;

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

		RepositoryFactory factory = this.getRepositoryFactory();
		return factory.openRepository(file, bare, this);
	}

	@Override
	public Repository createNewRepository(VFile file, boolean bare)
			throws XGitException {

		RepositoryFactory factory = this.getRepositoryFactory();
		return factory.createNewRepository(file, bare, this);
	}

}
