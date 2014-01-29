package ananas.xgitlite.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ananas.xgitlite.LocalRepo;
import ananas.xgitlite.LocalRepoFinder;
import ananas.xgitlite.LocalRepoFinder.Listener;
import ananas.xgitlite.XGitLite;

public class LocalRepoFinderFactory {

	public static LocalRepoFinder getUpFinder() {
		return new MyUpFinder();
	}

	static class MyDefaultListener implements Listener {

		@Override
		public void onFind(LocalRepo repo) {
		}

		@Override
		public void onDirectory(File path) {
		}
	}

	static class MyUpFinder implements LocalRepoFinder {

		@Override
		public LocalRepo[] find(File path, Listener listener) {
			if (listener == null) {
				listener = new MyDefaultListener();
			}
			String name = XGitLite.getInstance().getDefaultRepoDirectoryName();
			List<LocalRepo> list = new ArrayList<LocalRepo>();
			for (File p = path; p != null; p = p.getParentFile()) {
				listener.onDirectory(p);
				File dir = new File(p, name);
				if (dir.exists() && dir.isDirectory()) {
					LocalRepo repo = new LocalRepoImpl(dir);
					list.add(repo);
					listener.onFind(repo);
				}
			}
			return list.toArray(new LocalRepo[list.size()]);
		}

	}

}
