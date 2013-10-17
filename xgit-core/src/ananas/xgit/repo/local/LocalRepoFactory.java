package ananas.xgit.repo.local;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.RepoFactory;

public interface LocalRepoFactory extends RepoFactory {

	LocalRepo createRepo(VFile file);

	LocalRepo createRepo(VFile file, boolean bare);

}
