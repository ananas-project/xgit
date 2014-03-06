package ananas.xgit3.core.local;

public interface LocalModule {

	LocalRepoFinder newRepoFinder(String direction);

	LocalObjectBankFactory newBankFactory();

}
