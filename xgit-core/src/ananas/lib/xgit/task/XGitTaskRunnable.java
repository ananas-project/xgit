package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitException;

public interface XGitTaskRunnable {

	void run(XGitTaskContext context) throws XGitException;

	XGitTaskContext getTaskContext();

	void setTaskContext(XGitTaskContext context);

}
