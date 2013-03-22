package ananas.lib.xgit.impl;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.FileMonitor;

public class AbstractFileMonitor implements FileMonitor {

	private final VFile mFile;

	public AbstractFileMonitor(VFile file) {
		this.mFile = file;
	}

	@Override
	public VFile getFile() {
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
