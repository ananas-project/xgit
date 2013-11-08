package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;

public interface Indexer {

	LocalRepo getRepo();

	int scan(VFile dir, boolean r) throws IOException;

	IndexNode get(VFile file);

	VFile getIndexFile();

}
