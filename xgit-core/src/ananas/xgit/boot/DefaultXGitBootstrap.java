package ananas.xgit.boot;

import ananas.lib.util.SingletonLoader;

public class DefaultXGitBootstrap implements XGitBootstrap {

	private final XGitBootstrap _inner;

	public DefaultXGitBootstrap() {
		_inner = (XGitBootstrap) SingletonLoader.load(XGitBootstrap.class);
	}

	@Override
	public void boot() {
		_inner.boot();
	}

}
