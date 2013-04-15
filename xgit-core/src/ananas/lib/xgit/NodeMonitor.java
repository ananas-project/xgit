package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface NodeMonitor {

	VFile getFile();

	BaseRepository getBaseRepo();

}
