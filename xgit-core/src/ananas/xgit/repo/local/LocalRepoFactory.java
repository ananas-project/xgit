package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.XGitException;
import ananas.xgit.repo.RepoFactory;

public interface LocalRepoFactory extends RepoFactory {

	LocalRepo openRepo(VFile file) throws IOException, XGitException;

	LocalRepo initRepo(VFile file, boolean bare) throws IOException,
			XGitException;

}
