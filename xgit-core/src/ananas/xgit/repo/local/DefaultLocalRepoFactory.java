package ananas.xgit.repo.local;

import java.net.URI;

import ananas.lib.io.vfs.VFile;
import ananas.lib.util.SingletonLoader;
import ananas.xgit.repo.Repo;

public class DefaultLocalRepoFactory implements LocalRepoFactory {

	private final LocalRepoFactory _inner;

	public DefaultLocalRepoFactory() {
		_inner = (LocalRepoFactory) SingletonLoader
				.load(LocalRepoFactory.class);
	}

	@Override
	public Repo createRepo(URI uri) {
		return _inner.createRepo(uri);
	}

	@Override
	public LocalRepo createRepo(VFile file) {
		return _inner.createRepo(file);
	}

	@Override
	public LocalRepo createRepo(VFile file, boolean bare) {
		return _inner.createRepo(file, bare);
	}

}
