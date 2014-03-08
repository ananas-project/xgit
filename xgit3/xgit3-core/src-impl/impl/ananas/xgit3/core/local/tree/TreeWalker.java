package impl.ananas.xgit3.core.local.tree;

import java.io.File;

import ananas.xgit3.core.local.LocalRepo;

public class TreeWalker {

	private final String _repo_name;

	public TreeWalker(LocalRepo repo) {
		this._repo_name = repo.getPath().getName();
	}

	public void walk(File root, boolean _R, TreeNodeHandler h) {
		h.onWalkBegin(root);
		this.__do_walk(root, root, _R, h, 128);
		h.onWalkEnd(root);
	}

	private void __do_walk(File root, File path, boolean _R, TreeNodeHandler h,
			int depthLimit) {

		if (depthLimit < 0) {
			throw new RuntimeException("the path is too deep: " + path);
		}
		if (path.isDirectory()) {
			// this is a directory
			String name = path.getName();
			if (!name.equals(this._repo_name)) {
				h.onDirBegin(root, path);
				File[] chs = path.listFiles();
				for (File ch : chs) {
					if (_R) {
						this.__do_walk(root, ch, _R, h, depthLimit - 1);
					} else {
						if (!ch.isDirectory()) {
							this.__do_walk(root, ch, _R, h, depthLimit - 1);
						}
					}
				}
				h.onDirEnd(root, path);
			}
		} else {
			// this is a normal file
			h.onFile(root, path);
		}
	}

}
