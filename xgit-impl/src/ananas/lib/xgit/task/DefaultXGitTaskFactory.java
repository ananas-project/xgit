package ananas.lib.xgit.task;

import ananas.fileworks.Component;
import ananas.fileworks.task.TaskRunner;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;
import ananas.lib.xgit.task.ext.RepoRepair;

public class DefaultXGitTaskFactory implements XGitTaskFactory, Component {

	@Override
	public RepoInit doInit(XGitRepo repo) {
		RepoInit ret = new RepoInitImpl();
		this.setupContext(repo, ret);
		return ret;
	}

	private void setupContext(XGitRepo repo, XGitTaskRunnable runn) {

		TaskRunner runner = (TaskRunner) repo.getContext().getEnvironment()
				.getSingletonManager().get(TaskRunner.class, null);
		XGitTaskContext context = new DefaultXGitTaskContext(repo);
		context.setRunner(runner);
		context.setTaskRunnable(runn);
		runn.setTaskContext(context);
	}

	@Override
	public RepoCheck doCheck(XGitRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepoOpen doOpen(XGitRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepoRepair doRepair(XGitRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

}
