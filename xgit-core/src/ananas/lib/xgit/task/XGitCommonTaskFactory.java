package ananas.lib.xgit.task;

import ananas.lib.xgit.repo.Repo;
import ananas.lib.xgit.task.ext.RepoCheck;
import ananas.lib.xgit.task.ext.RepoInit;
import ananas.lib.xgit.task.ext.RepoOpen;
import ananas.lib.xgit.task.ext.RepoRepair;

public interface XGitCommonTaskFactory extends XGitTaskFactory {

	RepoInit init(Repo repo);

	RepoCheck check(Repo repo);

	RepoOpen open(Repo repo);

	RepoRepair repair(Repo repo);

}
