package ananas.xgit3.core.repo;

import java.net.URI;

public interface RepoDriverManager {

	RepoDriver getDriver(String className);

	/**
	 * get driver by scheme of the URI
	 * */
	RepoDriver getDriver(URI uri);

	void registerDriver(String scheme, String className);

	void registerDriver(String scheme, RepoDriver driver);

}
