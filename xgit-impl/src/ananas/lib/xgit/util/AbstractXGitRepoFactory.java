package ananas.lib.xgit.util;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentManager;
import ananas.fileworks.Context;
import ananas.fileworks.Environment;
import ananas.fileworks.util.DefaultContext;
import ananas.fileworks.util.DefaultEnvironment;
import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitRepoFactory;

public class AbstractXGitRepoFactory implements XGitRepoFactory, Component {

	private Environment mEnvi;

	@Override
	public XGitRepo createRepo(VFile path) {
		Environment envi = this.mEnvi;
		if (envi == null) {
			envi = new DefaultEnvironment();
			this.mEnvi = envi;
		}
		Context context = new DefaultContext(envi, path);
		ComponentManager singleton = context.getEnvironment()
				.getSingletonManager();
		singleton.put(XGitRepoFactory.class, null, this);
		return new XGitRepoImpl(context);
	}

}
