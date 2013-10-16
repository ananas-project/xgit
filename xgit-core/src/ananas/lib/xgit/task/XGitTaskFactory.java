package ananas.lib.xgit.task;

import ananas.lib.xgit.repo.Repo;

public interface XGitTaskFactory {

	XGitTask newTask(Repo repo, String name);

}
