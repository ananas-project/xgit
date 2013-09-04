package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface XGitRepoFactory {

	/**
	 * = invoke createRepo(VFile file, boolean bare); bare=false
	 * */
	XGitRepo createRepo(VFile file);

	XGitRepo createRepo(VFile file, boolean bare);

}
