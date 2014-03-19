package ananas.xgit3.core.repo;

import ananas.xgit3.core.bank.ObjectBank;

public interface Repo {

	ObjectBank getBank();

	RepoInfo getInfo();

	BranchManager getBranchManager();

}
