package ananas.lib.xgit.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.task.AbstractXGitTask;
import ananas.lib.xgit.task.DefaultXGitTaskFactory;
import ananas.lib.xgit.task.RepoCheckImpl;
import ananas.lib.xgit.task.RepoInitImpl;
import ananas.lib.xgit.task.RepoOpenImpl;
import ananas.lib.xgit.task.XGitTaskContext;
import ananas.lib.xgit.task.XGitTaskFactory;
import ananas.lib.xgit.task.XGitTaskRegistrar;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;

public class AbstractBootProc extends AbstractXGitTask {

	@Override
	public void run(XGitTaskContext context) throws XGitException {

		// register class

		ComponentRegistrar cr = context.getContext().getEnvironment()
				.getComponentRegistrar();

		cr.register(XGitTaskFactory.class, DefaultXGitTaskFactory.Factory.class);

		// declare var

		ComponentManager single = context.getContext().getEnvironment()
				.getSingletonManager();

		single.declare(XGitTaskFactory.class, null, true);

		// register task

		XGitTaskRegistrar taskReg = (XGitTaskRegistrar) single.get(
				XGitTaskRegistrar.class, null);
		taskReg.register(RepoInit.class, RepoInitImpl.Factory.class);
		taskReg.register(RepoOpen.class, RepoOpenImpl.Factory.class);
		taskReg.register(RepoCheck.class, RepoCheckImpl.Factory.class);

	}

}
