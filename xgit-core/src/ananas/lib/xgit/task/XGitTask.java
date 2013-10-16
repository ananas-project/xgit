package ananas.lib.xgit.task;

import ananas.lib.task.Task;
import ananas.lib.xgit.repo.Repo;

public interface XGitTask extends Task {

	Repo getRepo();

}
