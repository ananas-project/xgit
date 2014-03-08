package ananas.xgit3.core.local.tree;

import java.io.File;

import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalRepo;

public interface TreeMaker {

	LocalObject makeTree(File treeRoot);

	LocalObject makeTree(File treeRoot, boolean _R);

	LocalObject makeTree(File treeRoot, boolean _R, LocalRepo repo);

}
