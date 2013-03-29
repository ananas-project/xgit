package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.DirectoryMonitor;
import ananas.lib.xgit.Repository;

public class AbstractDirectoryMonitor implements DirectoryMonitor {

	private final VFile mFile;
	private final Repository mRepo;

	public AbstractDirectoryMonitor(Repository repo, VFile file) {
		this.mRepo = repo;
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

	@Override
	public Repository getRepository() {
		return this.mRepo;
	}

}
