package impl.ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.LocalRepoFinder;
import ananas.xgit3.core.local.LocalRepoFinderHandler;

final class DefaultLocalRepoFinder implements LocalRepoFinder {

	private String _direction;
	private String _name;

	public DefaultLocalRepoFinder() {
		this._name = ".git";
		this._direction = LocalRepoFinder.up;
	}

	@Override
	public LocalRepo findRepo(File file) {
		MyHandler h = new MyHandler();
		this.findRepo(file, 64, h);
		return h.getResult();
	}

	@Override
	public LocalRepo findRepo(File file, int depthLimit) {
		MyHandler h = new MyHandler();
		this.findRepo(file, depthLimit, h);
		return h.getResult();
	}

	@Override
	public void findRepo(File file, int depthLimit, LocalRepoFinderHandler h) {

		boolean isup = this._direction.equals(LocalRepoFinder.up);
		String name = this._name;
		h.begin();
		try {
			if (isup) {
				findUp(file, name, h);
			} else {
				findDown(file, name, depthLimit, h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		h.end();
	}

	private static void findDown(File file, String forName, int depthLimit,
			LocalRepoFinderHandler h) {

		boolean isdir = file.isDirectory();
		if (isdir) {
			if (forName.equals(file.getName())) {
				h.findTarget(file);
			}
		}
		if (depthLimit < 0) {
			throw new RuntimeException("the path is too deep: " + file);
		}
		if (isdir) {
			h.findInDirectory(file);
			File[] list = file.listFiles();
			for (File sub : list) {
				if (!h.keepFinding()) {
					break;
				}
				findDown(sub, forName, depthLimit - 1, h);
			}
		}
	}

	private static void findUp(File file, String name, LocalRepoFinderHandler h) {
		for (File p = file; p != null; p = p.getParentFile()) {
			if (!h.keepFinding()) {
				break;
			}
			h.findInDirectory(p);
			File p2 = new File(p, name);
			if (p2.exists()) {
				if (p2.isDirectory()) {
					h.findTarget(p2);
				}
			}
		}
	}

	class MyHandler implements LocalRepoFinderHandler {

		private LocalRepo _result;

		public LocalRepo getResult() {
			return this._result;
		}

		@Override
		public void begin() {
			System.out.println("find begin");
		}

		@Override
		public void findTarget(File path) {
			if (this._result == null) {
				LocalRepo repo = new DefaultLocalRepo(path);
				this._result = repo;
			}
		}

		@Override
		public boolean keepFinding() {
			return (this._result == null);
		}

		@Override
		public void findInDirectory(File path) {
			System.out.println("find repo in directory: " + path);
		}

		@Override
		public void end() {
			System.out.println("find end");
		}
	}

	@Override
	public void setDirection(String direction) {
		if (direction == null) {
			return;
		} else if (direction.equals(LocalRepoFinder.up)) {
		} else if (direction.equals(LocalRepoFinder.down)) {
		} else {
			return;
		}
		this._direction = direction;
	}

	@Override
	public String getDirection() {
		return this._direction;
	}

	@Override
	public String getTargetName() {
		return this._name;
	}

	@Override
	public void setTargetName(String targetName) {
		if (targetName == null) {
			return;
		}
		if (targetName.length() < 1) {
			return;
		}
		this._name = targetName;
	}

}
