package ananas.lib.xgit.util;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Context;
import ananas.fileworks.Environment;
import ananas.fileworks.task.TaskRunner;
import ananas.fileworks.util.DefaultContext;
import ananas.fileworks.util.DefaultEnvironment;
import ananas.fileworks.util.DefaultTaskRunner;
import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitRepoFactory;
import ananas.lib.xgit.task.XGitTaskRegistrar;
import ananas.lib.xgit.task.ext.RepoBoot;

public abstract class AbstractXGitRepoFactory implements XGitRepoFactory,
		Component {

	private final Class<?> m_boot_class;

	public AbstractXGitRepoFactory(Class<?> bootClass) {
		this.m_boot_class = bootClass;
	}

	@Override
	public final XGitRepo createRepo(VFile path) {
		Environment envi = new DefaultEnvironment();
		Context context = new DefaultContext(envi, path);
		try {
			this.doInit(context);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (XGitRepo) context.getComponentManager().get(XGitRepo.class,
				null);
	}

	private void doInit(Context context) throws XGitException {

		// register components

		ComponentManager single = context.getEnvironment()
				.getSingletonManager();
		ComponentManager cm = context.getComponentManager();
		ComponentRegistrar cr = context.getEnvironment()
				.getComponentRegistrar();

		cr.register(XGitRepo.class, XGitRepoImpl.Factory.class);
		cr.register(XGitTaskRegistrar.class,
				DefaultXGitTaskRegistrarFactory.class);
		cr.register(TaskRunner.class, DefaultTaskRunner.Factory.class);

		single.declare(XGitTaskRegistrar.class, null, true);
		single.declare(TaskRunner.class, null, true);
		cm.declare(XGitRepo.class, null, true);

		// register boot task
		XGitTaskRegistrar taskRegr = (XGitTaskRegistrar) context
				.getEnvironment().getSingletonManager()
				.get(XGitTaskRegistrar.class, null);
		taskRegr.register(RepoBoot.class, this.m_boot_class);

		// run

		XGitRepo repo = (XGitRepo) context.getComponentManager().get(
				XGitRepo.class, null);

		RepoBoot boot = (RepoBoot) taskRegr.createTask(repo, RepoBoot.class);
		boot.run(boot.getTaskContext());

	}
}
