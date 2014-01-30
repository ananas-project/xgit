package ananas.xgitlite.impl;

import java.io.File;
import java.io.InputStream;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;

class LocalObjectBankImpl implements LocalObjectBank {

	public LocalObjectBankImpl(File dir) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public LocalObject getObject(ObjectId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalObject addObject(String type, long length, InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalObject addObject(String type, File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
