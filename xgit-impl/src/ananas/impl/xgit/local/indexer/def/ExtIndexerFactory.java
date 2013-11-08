package ananas.impl.xgit.local.indexer.def;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.IndexerFactory;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexerFactory implements IndexerFactory {

	@Override
	public Indexer create(LocalRepo repo) {
		VFile dirRepo = repo.getRepoDirectory();
		VFileSystem vfs = dirRepo.getVFS();
		VFile dirIndex = vfs.newFile(dirRepo, "xgit/ext-index");
		return new ExtIndexerImpl(repo, dirIndex);
	}

}
