package ananas.xgit.repo.local;

import java.io.IOException;
import java.net.URI;

import ananas.lib.io.vfs.VFile;
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
	public Repo openRepo(URI uri) throws IOException, XGitException {
		return _inner.openRepo(uri);
	}

	@Override
	public LocalRepo openRepo(VFile file) throws IOException, XGitException {
		return _inner.openRepo(file);
	}

	@Override
	public LocalRepo initRepo(VFile file, boolean bare) throws IOException,
			XGitException {
		return _inner.initRepo(file, bare);
	}

}
