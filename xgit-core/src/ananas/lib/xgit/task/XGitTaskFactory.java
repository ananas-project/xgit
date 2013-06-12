package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;
import ananas.lib.xgit.task.ext.RepoRepair;

public interface XGitTaskFactory {

	RepoInit doInit(XGitRepo repo);

	RepoCheck doCheck(XGitRepo repo);

	RepoOpen doOpen(XGitRepo repo);

	RepoRepair doRepair(XGitRepo repo);

}
