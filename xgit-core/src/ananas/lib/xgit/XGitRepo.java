package ananas.lib.xgit;

import ananas.fileworks.node.Tree;

public interface XGitRepo extends Tree {

	XGitWorkspace getWorkspace();

	boolean isBare();

}
