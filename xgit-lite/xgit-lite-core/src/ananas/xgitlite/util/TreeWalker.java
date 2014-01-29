package ananas.xgitlite.util;

import java.io.File;
import java.io.FileFilter;

import ananas.xgitlite.XGitLite;

public class TreeWalker {

	interface Const {
		String gitignore = ".gitignore";
	}

	public void go(File file, FileFilter filter) {

		filter = new MyDefaultFilter(filter);

		this.__do_go(file, filter, 72);
	}

	private void __do_go(File file, FileFilter filter, int depthLimit) {

		if (depthLimit < 0) {
			throw new RuntimeException("the path is too deep.");
		}
		if (!filter.accept(file)) {
			return;
		}
		if (file.isDirectory()) {

			File ignore = new File(file, Const.gitignore);
			if (ignore.exists()) {
				// TODO ...
			}

			// iterate sub list
			String[] subs = file.list();
			java.util.Arrays.sort(subs);
			for (String s : subs) {
				File sf = new File(file, s);
				__do_go(sf, filter, depthLimit - 1);
			}
		}

	}

	class MyDefaultFilter implements FileFilter {

		private final FileFilter _filter;
		private final String _git_dir_name;

		public MyDefaultFilter(FileFilter filter) {
			this._filter = filter;
			this._git_dir_name = XGitLite.getInstance()
					.getDefaultRepoDirectoryName();
		}

		@Override
		public boolean accept(File path) {
			String name = path.getName();
			if (name.equals(this._git_dir_name)) {
				return false;
			}
			return this._filter.accept(path);
		}
	}
}
