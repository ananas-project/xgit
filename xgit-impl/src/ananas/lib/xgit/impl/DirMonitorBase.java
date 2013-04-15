package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.BaseRepository;
import ananas.lib.xgit.DirectoryMonitor;

public class DirMonitorBase implements DirectoryMonitor {

	private final BaseRepository mRepo;
	private final VFile mFile;

	public DirMonitorBase(DirMonitorContext dc) {
		this.mRepo = dc.getRepo();
		this.mFile = dc.getFile();
	}

	@Override
	public VFile getFile() {
		return this.mFile;
	}

	@Override
	public BaseRepository getBaseRepo() {
		return this.mRepo;
	}

}
