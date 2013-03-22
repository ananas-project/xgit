package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.DirectoryMonitor;

public class AbstractDirectoryMonitor implements DirectoryMonitor {

	private final VFile mFile;

	public AbstractDirectoryMonitor(VFile file) {
		this.mFile = file;
	}

	@Override
	public VFile getFile() {
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
