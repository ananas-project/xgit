package ananas.xgitlite;

import java.io.File;

public interface XGitLiteAPI {

	ObjectId createObjectId(String id);

	LocalRepo createLocalRepo(File path);

	LocalRepoFinder getRepoFinder(boolean up);

	String getDefaultRepoDirectoryName();

}
