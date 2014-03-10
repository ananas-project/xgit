package ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.ext.XGitExtends;
import ananas.xgit3.core.local.tree.TreeMaker;

public interface LocalRepo extends DirectoryNode {

	LocalObjectBank getBank();

	XGitExtends getXGitExtends();

	TreeMaker getTreeMaker();

	// objects

	LocalTree getTree(HashID id);

	LocalCommit getCommit(HashID id);

	LocalBlob getBlob(HashID id);

	LocalTag getTag(HashID id);

}
