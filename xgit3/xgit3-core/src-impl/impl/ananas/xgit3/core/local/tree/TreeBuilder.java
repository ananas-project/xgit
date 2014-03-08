package impl.ananas.xgit3.core.local.tree;

import java.io.File;

import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalRepo;

public class TreeBuilder {

	private LocalObject _root;
	private final LocalRepo _repo;

	public TreeBuilder(LocalRepo repo) {
		this._repo = repo;
	}

	public TreeNodeHandler getNodeHandler() {
		return this._handler;
	}

	public LocalObject getRoot() {
		return this._root;
	}

	private final TreeNodeHandler _handler = new TreeNodeHandler() {

		@Override
		public void onWalkBegin(File root) {
			// TODO Auto-generated method stub
			System.out.println(this + ".walkBegin, root:" + root);
		}

		@Override
		public void onDirBegin(File root, File dir) {
			// TODO Auto-generated method stub
			System.out.println("onDirBegin: " + dir);

		}

		@Override
		public void onFile(File root, File file) {
			// TODO Auto-generated method stub
			System.out.println("onFile:     " + file);

		}

		@Override
		public void onDirEnd(File root, File dir) {
			// TODO Auto-generated method stub
			System.out.println("onDirEnd:   " + dir);

		}

		@Override
		public void onWalkEnd(File root) {
			// TODO Auto-generated method stub
			System.out.println(this + ".walkEnd, root:" + root);

		}
	};

}
