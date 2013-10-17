package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class LocalObjectBankImpl implements LocalObjectBank {

	private final VFile _dir;

	public LocalObjectBankImpl(VFile dir) {
		this._dir = dir;
	}

	@Override
	public LocalObject getObject(ObjectId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalObject addObject(String type, long length, InputStream in)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
