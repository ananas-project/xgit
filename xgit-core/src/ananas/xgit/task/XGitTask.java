package ananas.xgit.task;

import ananas.lib.task.Task;
import ananas.xgit.repo.Repo;

public interface XGitTask extends Task {

	Repo getRepo();

}
