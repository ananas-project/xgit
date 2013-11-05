package ananas.xgit.repo;

import java.io.IOException;
import java.net.URI;

import ananas.lib.juke.Kernel;
import ananas.xgit.XGitException;

public interface RepoFactory {

	Repo openRepo(Kernel kernel, URI uri) throws IOException, XGitException;

}
