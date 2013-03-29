package ananas.lib.xgit.impl.extension;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.extension.XGitIndexFile;
import ananas.lib.xgit.impl.AbstractFileMonitor;

public class IndexFileImpl extends AbstractFileMonitor implements XGitIndexFile {

	public IndexFileImpl(Repository repo, VFile file) {
		super(repo, file);
	}

}
