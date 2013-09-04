package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;
import ananas.lib.xgit.task.ext.RepoRepair;

public interface XGitCommonTaskFactory extends XGitTaskFactory {

	RepoInit init(XGitRepo repo);

	RepoCheck check(XGitRepo repo);

	RepoOpen open(XGitRepo repo);

	RepoRepair repair(XGitRepo repo);

}
