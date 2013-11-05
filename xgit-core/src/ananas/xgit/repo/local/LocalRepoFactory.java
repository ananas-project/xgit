package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.lib.juke.Kernel;
import ananas.xgit.XGitException;
import ananas.xgit.repo.RepoFactory;

public interface LocalRepoFactory extends RepoFactory {

	LocalRepo openRepo(Kernel kernel, VFile file) throws IOException,
			XGitException;

	LocalRepo initRepo(Kernel kernel, VFile file, boolean bare)
			throws IOException, XGitException;

}
