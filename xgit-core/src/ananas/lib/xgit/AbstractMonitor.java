package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface AbstractMonitor {

	VFile getFile();

	boolean init();

	boolean check();

	Repository getRepository();

}
