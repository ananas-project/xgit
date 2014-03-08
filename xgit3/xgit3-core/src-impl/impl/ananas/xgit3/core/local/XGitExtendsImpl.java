package impl.ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.local.ext.FileTracker;
import ananas.xgit3.core.local.ext.XGitExtends;

public class XGitExtendsImpl implements XGitExtends {

	private final File _path;

	public XGitExtendsImpl(File dir) {
		this._path = dir;
	}

	@Override
	public File getPath() {
		return this._path;
	}

	@Override
	public FileTracker getFileTracker() {
		return new DefaultFileTracker(new File(_path, "file_tracker"));
	}

}
