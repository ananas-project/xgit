package ananas.lib.xgit;

public interface BaseRepository extends MonitorRegistrar {

	boolean isBare();

	XGitEnvironment getEnvironment();

	RepositoryDirectory getRepoDirectory();

}
