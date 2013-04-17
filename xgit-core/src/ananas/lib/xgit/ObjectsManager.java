package ananas.lib.xgit;

import ananas.lib.util.SHA1Value;

public interface ObjectsManager extends DirectoryMonitor {

	XGitObject getObject(SHA1Value key);

	XGitObject putObject(XGitObject obj);

}
