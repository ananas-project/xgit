package ananas.lib.xgit.task;

import ananas.fileworks.task.Task;
import ananas.lib.xgit.XGitRepo;

public interface XGitTaskContext extends Task {

	XGitRepo getRepo();

	XGitTaskRunnable getTaskRunnable();

	void setTaskRunnable(XGitTaskRunnable task);

	boolean isCancel();

}
