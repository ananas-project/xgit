package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.XGitException;

public interface Indexer {

	LocalRepo getRepo();

	IndexNode getNode(VFile file) throws XGitException;

	VFile getIndexDB();

	void add(IndexNode node, boolean r) throws IOException, XGitException;

}
