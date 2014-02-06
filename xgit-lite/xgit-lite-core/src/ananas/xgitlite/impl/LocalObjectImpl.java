package ananas.xgitlite.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.InflaterInputStream;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalObject;

class LocalObjectImpl implements LocalObject {

	private final ObjectId _id;
	private final File _file;
	private long _length;
	private String _type;

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
		try {
			this.__init_once();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this._type;
	}

	@Override
	public long getLength() {
		try {
			this.__init_once();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this._length;
	}

	private void __init_once() throws IOException {
		if (this._type == null) {
			InputStream in = this.openPlainInputStream();
			in.close();
		}
	}

	@Override
	public InputStream openPlainInputStream() throws IOException {
		MyPlainReader reader = new MyPlainReader(_file);
		InputStream in = reader.open();
		if (this._type == null) {
			this._length = reader.getLength();
			this._type = reader.getType();
		}
		return in;
	}

	@Override
	public InputStream openZipInputStream() throws IOException {
		return new FileInputStream(_file);
	}

	@Override
	public OutputStream openZipOutputStream() {
		// return new FileOutputStream(_file);
		return null;
	}

	@Override
	public boolean exists() {
		return _file.exists();
	}

	class MyPlainReader {

		private final File _file;
		private String _type;
		private long _length;

		public MyPlainReader(File file) {
			this._file = file;
		}

		public String getType() {
			return this._type;
		}

		public long getLength() {
			return this._length;
		}

		public InputStream open() throws IOException {

			final FileInputStream in0 = new FileInputStream(_file);
			final InflaterInputStream in = new InflaterInputStream(in0);

			StringBuilder sb = new StringBuilder();
			for (int b = in.read(); b > 0; b = in.read()) {
				sb.append((char) b);
			}
			String str = sb.toString();
			int i = str.lastIndexOf(" ");
			String s1 = str.substring(0, i);
			String s2 = str.substring(i + 1);
			this._type = s1;
			this._length = Long.parseLong(s2);
			return in;
		}
	}
}
