package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.WorkingDirectory;

public class WorkingDirectoryImpl extends AbstractDirectoryMonitor implements
		WorkingDirectory {

	public WorkingDirectoryImpl(VFile file) {
		super(file);

	}

}
