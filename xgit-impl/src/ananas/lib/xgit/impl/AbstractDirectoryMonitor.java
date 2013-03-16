package ananas.lib.xgit.impl;

import java.io.File;

import ananas.lib.xgit.DirectoryMonitor;

public class AbstractDirectoryMonitor implements DirectoryMonitor {

	private final File mFile;

	public AbstractDirectoryMonitor(File file) {
		this.mFile = file;
	}

	@Override
	public File getFile() {
		return this.mFile;
	}

	@Override
	public boolean init() {
		if (this.mFile.exists()) {
			return false;
		} else {
			return this.mFile.mkdirs();
		}
	}

	@Override
	public boolean check() {
		if (this.mFile.exists()) {
			if (this.mFile.isDirectory()) {
				return true;
			}
		}
		
		
		if (this.mFile.exists()) {
			if (this.mFile.isDirectory()) {
				return true;
			}
		}

		
		return false;
	}

}
