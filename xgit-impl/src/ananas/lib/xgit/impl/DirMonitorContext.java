package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.BaseRepository;

interface DirMonitorContext {

	BaseRepository getRepo();

	VFile getFile();

}
