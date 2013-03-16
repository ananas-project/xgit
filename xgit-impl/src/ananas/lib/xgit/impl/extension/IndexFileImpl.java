package ananas.lib.xgit.impl.extension;

import java.io.File;

import ananas.lib.xgit.extension.XGitIndexFile;
import ananas.lib.xgit.impl.AbstractFileMonitor;

public class IndexFileImpl extends AbstractFileMonitor implements XGitIndexFile {

	public IndexFileImpl(File file) {
		super(file);
	}

}
