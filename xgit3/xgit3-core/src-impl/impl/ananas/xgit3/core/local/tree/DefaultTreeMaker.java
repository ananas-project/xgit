package impl.ananas.xgit3.core.local.tree;

import java.io.File;

import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.LocalRepoFinder;
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
	public LocalObject makeTree(File root, boolean _R, LocalRepo repo) {
		if (repo == null) {
			LocalRepoFinder finder = LocalRepoFinder.Factory
					.getFinder(LocalRepoFinder.up);
			repo = finder.findRepo(root);
			if (repo == null) {
				throw new RuntimeException("cannot find repo in the path: "
						+ root);
			}
		}
		if (root == null) {
			root = repo.getPath();
		}
		TreeBuilder builder = new TreeBuilder(repo);
		TreeWalker walker = new TreeWalker(repo);
		walker.walk(root, _R, builder.getNodeHandler());
		return builder.getRoot();
	}

}
