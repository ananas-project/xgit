package ananas.lib.xgit;

import java.util.List;

import ananas.lib.io.vfs.VFile;

public interface XGitWorkspace {

	// outer
	String dir_working = "..";
	String dir_main = ".";

	// inner
	String dir_branches = "branches";
	String dir_hooks = "hooks";
	String dir_logs = "logs";
	String dir_objects = "objects";
	String dir_refs = "refs";
	String dir_xgit = "xgit";

	String dir_x_tmp = "xgit/tmp";

	String file_config = "config";
	String file_description = "description";
	String file_FETCH_HEAD = "FETCH_HEAD";
	String file_HEAD = "HEAD";
	String file_index = "index";

	// methods

	VFile getMainDirectory();

	VFile getFile(String key);

	XGitFileInfo getFileInfo(String key);

	List<String> listKeys();

}
