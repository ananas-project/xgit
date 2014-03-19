package ananas.xgit3.core.repo;

public interface RepoDriver {

	RepoFinder getFinder();

	Repo open(RepoInfo info);

	Repo init(RepoInfo info);

}
