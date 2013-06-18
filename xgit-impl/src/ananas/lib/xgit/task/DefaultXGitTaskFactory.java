package ananas.lib.xgit.task;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentFactory;
import ananas.fileworks.Context;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;
import ananas.lib.xgit.task.ext.RepoRepair;

public class DefaultXGitTaskFactory implements XGitTaskFactory, Component {

	public static class Factory implements ComponentFactory {

		@Override
		public Component createComponent(Context context) {
			return new DefaultXGitTaskFactory();
		}

	}

	private XGitTaskRunnable __newTask(XGitRepo repo, Class<?> api) {

		XGitTaskRegistrar taskRegr = (XGitTaskRegistrar) repo.getContext()
				.getEnvironment().getSingletonManager()
				.get(XGitTaskRegistrar.class, null);

		return taskRegr.createTask(repo, api);
	}

	@Override
	public RepoInit doInit(XGitRepo repo) {
		return (RepoInit) this.__newTask(repo, RepoInit.class);
	}

	@Override
	public RepoCheck doCheck(XGitRepo repo) {
		return (RepoCheck) this.__newTask(repo, RepoCheck.class);
	}

	@Override
	public RepoOpen doOpen(XGitRepo repo) {
		return (RepoOpen) this.__newTask(repo, RepoOpen.class);
	}

	@Override
	public RepoRepair doRepair(XGitRepo repo) {
		return (RepoRepair) this.__newTask(repo, RepoRepair.class);
	}

}
