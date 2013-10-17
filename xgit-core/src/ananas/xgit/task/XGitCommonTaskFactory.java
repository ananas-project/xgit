package ananas.xgit.task;

import ananas.xgit.repo.Repo;
import ananas.xgit.task.ext.RepoCheck;
import ananas.xgit.task.ext.RepoInit;
import ananas.xgit.task.ext.RepoOpen;
import ananas.xgit.task.ext.RepoRepair;

public interface XGitCommonTaskFactory extends XGitTaskFactory {

	RepoInit init(Repo repo);

	RepoCheck check(Repo repo);

	RepoOpen open(Repo repo);

	RepoRepair repair(Repo repo);

}
