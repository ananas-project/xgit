package impl.ananas.xgit3.core.local;

import ananas.xgit3.core.local.LocalModule;
import ananas.xgit3.core.local.LocalRepoFinder;

public class ThisModule implements LocalModule {

	@Override
	public LocalRepoFinder newRepoFinder(String direction) {
		LocalRepoFinder finder = new DefaultLocalRepoFinder();
		finder.setDirection(direction);
		return finder;
	}

}
