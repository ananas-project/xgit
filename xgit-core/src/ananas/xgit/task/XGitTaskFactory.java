package ananas.xgit.task;

import ananas.xgit.repo.Repo;

public interface XGitTaskFactory {

	XGitTask newTask(Repo repo, String name);

}
