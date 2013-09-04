package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitRepo;

public interface XGitTaskFactory {

	XGitTask newTask(XGitRepo repo, String name);

}
