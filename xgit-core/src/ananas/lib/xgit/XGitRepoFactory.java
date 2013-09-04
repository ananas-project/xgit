package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;
import ananas.lib.util.SingletonLoader;

public interface XGitRepoFactory {

	/**
	 * = invoke createRepo(VFile file, boolean bare); bare=false
	 * */
	XGitRepo createRepo(VFile file);

	XGitRepo createRepo(VFile file, boolean bare);

	class Util {

		private static XGitRepoFactory inst;

		public static XGitRepoFactory getDefaultFactory() {
			XGitRepoFactory factory = inst;
			if (factory == null) {
				factory = (XGitRepoFactory) SingletonLoader
						.load(XGitRepoFactory.class);
				inst = factory;
			}
			return factory;
		}

	}

}
