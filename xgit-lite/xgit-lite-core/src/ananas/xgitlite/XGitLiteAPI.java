package ananas.xgitlite;

import java.io.File;

import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoFinder;

public interface XGitLiteAPI {

	ObjectId createObjectId(String id);

	LocalRepo createLocalRepo(File path);

	LocalRepoFinder getRepoFinder(boolean up);

	String getDefaultRepoDirectoryName();

}
