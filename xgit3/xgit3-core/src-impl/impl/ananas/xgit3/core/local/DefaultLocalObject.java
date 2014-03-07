package impl.ananas.xgit3.core.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.InflaterInputStream;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;

public class DefaultLocalObject implements LocalObject {

	private final HashID _id;
	private final File _path;
	private final LocalObjectBank _bank;
	private Meta _meta;

	public DefaultLocalObject(LocalObjectBank bank, HashID id, File file) {
		_id = id;
		_path = file;
		_bank = bank;
	}

	@Override
	public File getPath() {
		return this._path;
	}

	@Override
	public InputStream openPlainInputStream() throws IOException {
		InputStream in1 = this.openZippedInputStream();
		InputStream in2 = new InflaterInputStream(in1);
		Meta meta = new Meta();
		meta.loadHead(in2);
		this._meta = meta;
		return in2;
	}

	@Override
	public InputStream openZippedInputStream() throws IOException {
		return new FileInputStream(_path);
	}

	@Override
	public OutputStream openZippedOutputStream() throws IOException {
		SuperLocalObjectBuilder builder = SuperLocalObjectBuilder.Factory
				.newZippedBuilder(this);
		return builder.openOutputStream(null, 0);
	}

	@Override
	public long length() {
		Meta meta = this.getMeta();
		return meta.length();
	}

	@Override
	public String type() {
		Meta meta = this.getMeta();
		return meta.type();
	}

	private Meta getMeta() {
		Meta meta = this._meta;
		if (meta == null) {
			try {
				InputStream in = this.openPlainInputStream();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			meta = this._meta;
		}
		return meta;
	}

	@Override
	public HashID id() {
		return this._id;
	}

	@Override
	public LocalObjectBank ownerBank() {
		return this._bank;
	}

	@Override
	public boolean exists() {
		return this._path.exists();
	}

	class Meta {

		private long _length;
		private String _type;

		public String type() {
			return this._type;
		}

		public void loadHead(InputStream in) throws IOException {
			StringBuilder sb = new StringBuilder();
			String type = null;
			String len = null;
			for (int tout = 128; tout > 0; tout--) {
				final int b = in.read();
				if (b > 0) {
					char ch = (char) b;
					sb.append(ch);
				} else if (b == 0) {
					int i = sb.lastIndexOf(" ");
					type = sb.substring(0, i);
					len = sb.substring(i + 1);
					break;
				} else /* if (b<0) */{
					break;
				}
			}
			this._length = Long.parseLong(len);
			this._type = type;
		}

		public long length() {
			return this._length;
		}
	}

}
