package ananas.xgitlite.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;

class LocalObjectImpl implements LocalObject {

	private final ObjectId _id;
	private final File _file;

	public LocalObjectImpl(ObjectId id, File file) {
		this._id = id;
		this._file = file;
	}

	@Override
	public ObjectId getId() {
		return _id;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InputStream openPlainInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream openZipInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream openZipOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		return _file.exists();
	}

}
