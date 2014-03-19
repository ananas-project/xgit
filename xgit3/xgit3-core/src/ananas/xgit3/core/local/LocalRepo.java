package ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.ext.XGitExtends;
import ananas.xgit3.core.local.tree.TreeMaker;
import ananas.xgit3.core.repo.Repo;

public interface LocalRepo extends DirectoryNode, Repo {

	LocalObjectBank getBankLF();

	XGitExtends getXGitExtends();

	TreeMaker getTreeMaker();

	// objects

	LocalTree getTree(HashID id);

	LocalCommit getCommit(HashID id);

	LocalBlob getBlob(HashID id);

	LocalTag getTag(HashID id);

}
