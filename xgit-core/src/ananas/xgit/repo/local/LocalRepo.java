package ananas.xgit.repo.local;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.Repo;

public interface LocalRepo extends Repo {

	LocalRepoFactory getFactory();

	LocalObjectBank getObjectBank();

	VFile getRepoDirectory();

}
