package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IndexNode {

	Indexer getIndexer();

	VFile getTargetFile();

	// operate

	void add() throws IOException;

	// meta

	ObjectId getObjectId();

	String getType();

	long getLength();

	long lastModified();

}
