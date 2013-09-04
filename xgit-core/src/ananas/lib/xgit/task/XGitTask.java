package ananas.lib.xgit.task;

import ananas.lib.task.Task;
import ananas.lib.xgit.XGitRepo;

public interface XGitTask extends Task {

	XGitRepo getRepo();

}
