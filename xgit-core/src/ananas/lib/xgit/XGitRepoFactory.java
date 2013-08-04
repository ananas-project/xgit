package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;
import ananas.lib.util.SingletonLoader;

public interface XGitRepoFactory {

	XGitRepo createRepo(VFile file);

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
