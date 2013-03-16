package ananas.lib.xgit.impl;

import java.io.File;
import java.io.IOException;

import ananas.lib.xgit.FileMonitor;

public class AbstractFileMonitor implements FileMonitor {

	private final File mFile;

	public AbstractFileMonitor(File file) {
		this.mFile = file;
	}

	@Override
	public File getFile() {
		return this.mFile;
	}

	@Override
	public boolean init() {
		if (!this.mFile.exists()) {
			try {
				this.mFile.getParentFile().mkdirs();
				return this.mFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean check() {
		if (this.mFile.exists()) {
			if (this.mFile.isFile()) {
				return true;
			}
		}
		return false;
	}

}
