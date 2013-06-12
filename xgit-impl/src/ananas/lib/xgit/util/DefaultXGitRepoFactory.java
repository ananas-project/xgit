package ananas.lib.xgit.util;

import ananas.fileworks.Context;
import ananas.fileworks.Environment;
import ananas.fileworks.task.Task;
import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.boot.XGitBooterFactory;

public class DefaultXGitRepoFactory extends AbstractXGitRepoFactory {

	@Override
	public XGitRepo createRepo(VFile path) {

		XGitRepo repo = super.createRepo(path);
		Context context = repo.getContext();
		Environment envi = context.getEnvironment();

		envi.getComponentRegistrar().register(XGitBooterFactory.class,
				DefaultXGitBooterFactory.class);

		XGitBooterFactory booterf = (XGitBooterFactory) envi
				.getComponentRegistrar().createComponent(
						XGitBooterFactory.class);
		Task booter = booterf.createTask(context);
		booter.getRunnable().run();

		return repo;
	}

}
