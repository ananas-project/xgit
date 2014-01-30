package ananas.xgitlite.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;

  class LocalObjectImpl implements LocalObject {

	public LocalObjectImpl(ObjectId id, File file) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

}
