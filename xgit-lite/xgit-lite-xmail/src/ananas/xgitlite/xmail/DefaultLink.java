package ananas.xgitlite.xmail;

import java.io.File;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;

public class DefaultLink implements Link {

	private final LocalObject _obj;
	private String _name;
	private String _type;
	private File _file;

	public DefaultLink(LocalObject obj) {
		this._obj = obj;
	}

	@Override
	public String name() {
		return this._name;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public File local_file() {
		return this._file;
	}

	@Override
	public String type() {
		return this._type;
	}

	@Override
	public ObjectId id() {
		return this._obj.getId();
	}

}
