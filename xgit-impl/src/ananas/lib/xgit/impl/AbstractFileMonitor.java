package ananas.lib.xgit.impl;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.FileMonitor;
import ananas.lib.xgit.Repository;

public class AbstractFileMonitor implements FileMonitor {

	private final VFile mFile;
	private final Repository mRepo;

	public AbstractFileMonitor(Repository repo, VFile file) {
		this.mFile = file;
		this.mRepo = repo;
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

	@Override
	public Repository getRepository() {
		return this.mRepo;
	}

}
