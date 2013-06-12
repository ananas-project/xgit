package ananas.lib.xgit;

import ananas.fileworks.Context;
import ananas.lib.xgit.task.XGitTaskFactory;

public interface XGitRepo {

	Context getContext();

	XGitWorkspace getWorkspace();

	XGitTaskFactory getTaskFactory();

}
