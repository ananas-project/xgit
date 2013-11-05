package ananas.xgit.repo.local;

import java.io.IOException;
import java.net.URI;

import ananas.lib.io.vfs.VFile;
import ananas.lib.juke.Kernel;
import ananas.lib.util.SingletonLoader;
import ananas.xgit.XGitException;
import ananas.xgit.repo.Repo;

public class DefaultLocalRepoFactory implements LocalRepoFactory {

	private final LocalRepoFactory _inner;

	public DefaultLocalRepoFactory() {
		_inner = (LocalRepoFactory) SingletonLoader
				.load(LocalRepoFactory.class);
	}

	@Override
	public Repo openRepo(Kernel kernel, URI uri) throws IOException,
			XGitException {
		return _inner.openRepo(kernel, uri);
	}

	@Override
	public LocalRepo openRepo(Kernel kernel, VFile file) throws IOException,
			XGitException {
		return _inner.openRepo(kernel, file);
	}

	@Override
	public LocalRepo initRepo(Kernel kernel, VFile file, boolean bare)
			throws IOException, XGitException {
		return _inner.initRepo(kernel, file, bare);
	}

}
