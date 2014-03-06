package impl.ananas.xgit3.core.local;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.InflaterOutputStream;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.util.AbstractOutputStream;
import ananas.xgit3.core.util.HashOutputStream;

public interface SuperLocalObjectBuilder {

	class Factory {

		public static SuperLocalObjectBuilder newBuilder(LocalObject obj) {
			return new BuilderForZipped(obj);
		}

		public static SuperLocalObjectBuilder newBuilder(LocalObjectBank bank) {
			return new BuilderForPlain(bank);
		}

	}

	void begin(String type, long length);

	OutputStream getOutput();

	LocalObject end();

	class BuilderForPlain implements SuperLocalObjectBuilder {

		private final LocalObjectBank _bank;

		public BuilderForPlain(LocalObjectBank bank) {
			this._bank = bank;
		}

		@Override
		public void begin(String type, long length) {
			// TODO Auto-generated method stub

		}

		@Override
		public OutputStream getOutput() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalObject end() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	class BuilderForZipped implements SuperLocalObjectBuilder {

		private final LocalObject _object;
		private OutputStream _master_out;
		private HashOutputStream _hash_out;
		private ZippedOutputStream _zip_out;

		public BuilderForZipped(LocalObject obj) {
			this._object = obj;
		}

		@Override
		public void begin(String type, long length) {

			HashAlgorithmProvider hash_prov = this._object.ownerBank()
					.getHashAlgorithmProvider();

			ZippedOutputStream zip_out = Helper.newInstance(length,
					this._object.exists());
			HashOutputStream hash_out = new HashOutputStream(hash_prov);
			HeaderParser hdr = new HeaderParser();
			The2WayOutputStream plain2way = new The2WayOutputStream(hash_out,
					hdr);
			InflaterOutputStream unzip = new InflaterOutputStream(plain2way);
			The2WayOutputStream zip2way = new The2WayOutputStream(unzip,
					zip_out.getOutput());

			this._hash_out = hash_out;
			this._master_out = zip2way;
			this._zip_out = zip_out;
		}

		@Override
		public OutputStream getOutput() {
			return this._master_out;
		}

		@Override
		public LocalObject end() {
			try {
				OutputStream out = this.getOutput();
				out.flush();
				out.close();
				HashID id1 = this._hash_out.getResultId();
				HashID id2 = this._object.id();
				boolean save = true;
				if (!id1.equals(id2)) {
					save = false;
				}
				if (save) {
					this._zip_out.saveTo(this._object.getPath());
				} else {
					this._zip_out.drop();
				}
				return this._object;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	static class HeaderParser extends AbstractOutputStream {

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			// TODO Auto-generated method stub

		}

	}

	static class Helper {

		public static ZippedOutputStream newInstance(long length, boolean exists) {

			if (exists) {
				return new NopZippedOutputStream();
			} else {
				if (length < 1024 * 128) {
					return new SmallZippedOutputStream();
				} else {
					return new LargeZippedOutputStream();
				}
			}
		}

	}

	interface ZippedOutputStream {

		OutputStream getOutput();

		void saveTo(File dest);

		void drop();

	}

	static class SmallZippedOutputStream implements ZippedOutputStream {

		@Override
		public void saveTo(File dest) {
			// TODO Auto-generated method stub

		}

		@Override
		public void drop() {
			// TODO Auto-generated method stub

		}

		@Override
		public OutputStream getOutput() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	static class LargeZippedOutputStream implements ZippedOutputStream {

		@Override
		public OutputStream getOutput() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void saveTo(File dest) {
			// TODO Auto-generated method stub

		}

		@Override
		public void drop() {
			// TODO Auto-generated method stub

		}

	}

	static class NopZippedOutputStream implements ZippedOutputStream {

		final OutputStream out = new OutputStream() {

			@Override
			public void write(int b) throws IOException {
			}

			@Override
			public void write(byte[] buf, int off, int len) throws IOException {
			}
		};

		@Override
		public OutputStream getOutput() {
			return out;
		}

		@Override
		public void saveTo(File dest) {
		}

		@Override
		public void drop() {
		}

	}

	static class The2WayOutputStream extends AbstractOutputStream {

		private final OutputStream _out1;
		private final OutputStream _out2;

		public The2WayOutputStream(OutputStream out1, OutputStream out2) {
			this._out1 = out1;
			this._out2 = out2;
		}

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			_out1.write(b, off, len);
			_out2.write(b, off, len);
		}

		@Override
		public void close() throws IOException {
			_out1.close();
			_out2.close();
		}

		@Override
		public void flush() throws IOException {
			_out1.flush();
			_out2.flush();
		}

	}

}
