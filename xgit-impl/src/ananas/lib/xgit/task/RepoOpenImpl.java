package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoOpen;

public class RepoOpenImpl extends AbstractXGitTask implements RepoOpen {

	@Override
	public void run(XGitTaskContext context) throws XGitException {

		System.out.println(this + ".run(...)");

		final XGitRepo repo = context.getRepo();
		RepoCheck aCheck = repo.getTaskFactory().doCheck(repo);
		aCheck.run(aCheck.getTaskContext());

	}

}
