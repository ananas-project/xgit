package ananas.lib.xgit;

import ananas.lib.io.vfs.VFile;

public interface XGitFileInfo {

	VFile getFile();

	boolean isRequired();

	boolean isDirectory();

}
