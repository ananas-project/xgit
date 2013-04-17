package ananas.lib.xgit.impl;

import ananas.lib.io.vfs.VFile;
import ananas.lib.util.SHA1Value;
import ananas.lib.xgit.ObjectsManager;
import ananas.lib.xgit.XGitObject;

public class TheObjectsManagerImpl extends DirMonitorBase implements
		ObjectsManager {

	public TheObjectsManagerImpl(DirMonitorContext dc) {
		super(dc);
	}

	@Override
	public XGitObject getObject(SHA1Value hash) {
		VFile file = this._getFileByHash(hash);
		if (!file.exists()) {
			return null;
		}
		return new XGitObjectImpl(hash, file);
	}

	private VFile _getFileByHash(SHA1Value hash) {
		String str = hash.toString(false);
		int index = 2;
		String s0 = str.substring(0, index);
		String s2 = str.substring(index);
		VFile base = this.getFile();
		return base.getVFS().newFile(base, s0 + "/" + s2);
	}

	@Override
	public XGitObject putObject(XGitObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
