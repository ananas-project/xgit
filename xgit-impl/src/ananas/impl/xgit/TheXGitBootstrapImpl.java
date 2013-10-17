package ananas.impl.xgit;

import ananas.lib.util.PropertiesLoader;
import ananas.xgit.boot.XGitBootstrap;

public class TheXGitBootstrapImpl implements XGitBootstrap {

	@Override
	public void boot() {

		String name = this.getClass().getSimpleName() + ".properties";
		PropertiesLoader.Util.loadPropertiesToSystem(this, name);

	}

}
