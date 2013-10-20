package ananas.impl.xgit.local;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.ExtIndexInfo;

public class ExtIndexInfoImpl implements ExtIndexInfo {

	private final VFile _target_file;

	public ExtIndexInfoImpl(VFile target) {
		this._target_file = target;
	}

	@Override
	public VFile getTargetFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectId getObjectId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

		System.out.println("add " + _target_file);

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
