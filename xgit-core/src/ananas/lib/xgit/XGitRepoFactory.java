package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface XGitRepoFactory {

	XGitRepo createRepo(VFile file);

}
