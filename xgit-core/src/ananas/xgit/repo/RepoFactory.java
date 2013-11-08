package ananas.xgit.repo;

import java.io.IOException;
import java.net.URI;

import ananas.xgit.XGitException;

public interface RepoFactory {

	Repo openRepo(URI uri) throws IOException, XGitException;

}
