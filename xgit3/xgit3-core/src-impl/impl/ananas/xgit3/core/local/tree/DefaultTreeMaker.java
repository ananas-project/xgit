package impl.ananas.xgit3.core.local.tree;

import java.io.File;

import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.tree.TreeMaker;

public class DefaultTreeMaker implements TreeMaker {

	@Override
	public LocalObject makeTree(File treeRoot) {
		return this.makeTree(treeRoot, true, null);
	}

	@Override
	public LocalObject makeTree(File treeRoot, boolean _R) {
		return this.makeTree(treeRoot, _R, null);
	}

	@Override
	public LocalObject makeTree(File treeRoot, boolean _R, LocalRepo repo) {
		// TODO Auto-generated method stub
		return null;
	}

}
