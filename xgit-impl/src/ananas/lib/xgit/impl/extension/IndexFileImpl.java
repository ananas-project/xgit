package ananas.lib.xgit.impl.extension;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.extension.XGitIndexFile;
import ananas.lib.xgit.impl.AbstractFileMonitor;

public class IndexFileImpl extends AbstractFileMonitor implements XGitIndexFile {

	public IndexFileImpl(VFile file) {
		super(file);
	}

}
