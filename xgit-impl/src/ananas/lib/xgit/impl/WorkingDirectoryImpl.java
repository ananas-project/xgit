package ananas.lib.xgit.impl;

import java.io.File;

import ananas.lib.xgit.WorkingDirectory;

public class WorkingDirectoryImpl extends AbstractDirectoryMonitor implements WorkingDirectory {

	public WorkingDirectoryImpl(File file) {
		super(file);

	}

}
