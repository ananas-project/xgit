package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.InflaterInputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalObjectBank;

public class LocalObjectImpl implements LocalObject {

	private final LocalObjectBank _bank;
	private final ObjectId _id;
	private final VFile _file;
	private String _type;
	private long _length;

	public LocalObjectImpl(LocalObjectBank bank, ObjectId id) {
		this._bank = bank;
		this._id = id;

		String p1, p2, ids;
		ids = id.toString();
		int index = 2;
		p1 = ids.substring(0, index);
		p2 = ids.substring(index);

		VFile dir = bank.getBankDirectory();
		VFileSystem vfs = dir.getVFS();
		this._file = vfs.newFile(dir, p1 + vfs.separator() + p2);

	}

	@Override
	public ObjectId id() {
		return this._id;
	}

	@Override
	public boolean exists() {
		return _file.exists();
	}

	@Override
	public String type() {
		this.__load_meta();
		String type = this._type;
		if (type == null)
			type = "";
		return type;
	}

	@Override
	public long length() {
		this.__load_meta();
		return this._length;
	}

	@Override
	public LocalObjectBank getBank() {
		return this._bank;
	}

	private void __load_meta() {
		if (this._type != null)
			return;
		if (!_file.exists())
			return;
		try {
			InputStream in = this.openRawInputStream();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public InputStream openInputStream() {
		return new VFileInputStream(this._file);
	}

	@Override
	public OutputStream openOutputStream() {
		if (this._file.exists()) {
			return new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					// do nothing
				}
			};
		}
		return new ZipOutputStream();
	}

	@Override
	public InputStream openRawInputStream() throws IOException {
		InputStream zip_in = new VFileInputStream(this._file);
		InputStream raw_in = new InflaterInputStream(zip_in);
		InputStream in = raw_in;
		if (this._type != null) {
			for (int b = in.read(); b > 0; b = in.read()) {
				// do nothing
			}
		} else {
			StringBuilder sb = new StringBuilder();
			int iSpace = 0;
			for (int b = in.read(); b > 0; b = in.read()) {
				// load meta
				if (b == ' ') {
					iSpace = sb.length();
				}
				sb.append((char) b);
			}
			String type = sb.substring(0, iSpace).trim();
			String length = sb.substring(iSpace + 1).trim();
			this._type = type;
			this._length = Long.parseLong(length);
		}
		return in;
	}

	class ZipOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			// TODO Auto-generated method stub
			throw new RuntimeException("no impl");
		}

		public void close() {
			// TODO Auto-generated method stub
			throw new RuntimeException("no impl");
		}
	}

	@Override
	public VFile getZipFile() {
		return _file;
	}

}
