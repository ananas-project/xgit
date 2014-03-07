package impl.ananas.xgit3.core.local;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.util.AbstractOutputStream;

public interface LocalObjectBuilder {

	class Factory {

		public static LocalObjectBuilder newZippedBuilder(LocalObject obj) {
			return new BuilderForZipped(obj);
		}

		public static LocalObjectBuilder newPlainBuilder(
				LocalObjectBank bank) {
			return new BuilderForPlain(bank);
		}

	}

	OutputStream openOutputStream(String type, long length) throws IOException;

	LocalObject getResult();

	interface HashSUM {

		OutputStream getOutput();

		HashID getResultId();

		long getLength();
	}

	interface HeaderBuffer {

		OutputStream getZippedOutput();

		OutputStream getPlainOutput();

		FinalOutput getFinalOutput();

	}

	interface FinalOutput {

		OutputStream getOutput() throws IOException;

		void saveTo(File dest) throws IOException;

		void drop();

	}

	class InnerFactory {

		static HashSUM newHashSum(HashAlgorithmProvider hap) {
			return new ImplHashSumOutput(hap);
		}

		static OutputStream new2WayOutput(OutputStream o1, OutputStream o2) {
			return new Impl2WayOutputStream(o1, o2);
		}

		static HeaderBuffer newHeaderBuffer(LocalObject obj) {
			return new ImplHeaderBuffer(obj);
		}

		static FinalOutput newFinalOutput(File tmp, long length, LocalObject obj) {
			if (obj != null)
				if (obj.exists()) {
					return new NopZippedOutputStream();
				}
			if (length < 1024 * 128) {
				return new SmallZippedOutputStream(tmp);
			} else {
				return new LargeZippedOutputStream(tmp);
			}
		}

		static OutputStream newUnzip(OutputStream out) {
			return new InflaterOutputStream(out);
		}

		static OutputStream newZip(OutputStream out) {
			return new DeflaterOutputStream(out);
		}

	}

	class BuilderForPlain extends AbstractOutputStream implements
			LocalObjectBuilder {

		private final LocalObjectBank _bank;
		private LocalObject _object;
		private OutputStream _master_out;
		private FinalOutput _final_out;
		private HashSUM _hash_out;
		private long _want_length;

		public BuilderForPlain(LocalObjectBank bank) {
			this._bank = bank;
		}

		@Override
		public OutputStream openOutputStream(String type, long length)
				throws IOException {
			File tmp = _bank.newTempFile(type + " " + length);
			HashAlgorithmProvider hap = _bank.getHashAlgorithmProvider();
			HashSUM hash = InnerFactory.newHashSum(hap);
			FinalOutput final_out = InnerFactory.newFinalOutput(tmp, length,
					null);
			OutputStream zip = InnerFactory.newZip(final_out.getOutput());
			OutputStream master_out = InnerFactory.new2WayOutput(zip,
					hash.getOutput());
			String hdr = type + " " + length + "\0";
			{
				Writer wtr = new OutputStreamWriter(master_out, "UTF-8");
				wtr.append(hdr);
				wtr.flush();
			}
			this._want_length = hdr.length() + length;
			this._master_out = master_out;
			this._final_out = final_out;
			this._hash_out = hash;
			return this;
		}

		@Override
		public LocalObject getResult() {
			return this._object;
		}

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			this._master_out.write(b, off, len);
		}

		@Override
		public void flush() throws IOException {
			this._master_out.flush();
		}

		@Override
		public void close() throws IOException {
			this._master_out.close();

			HashID id = this._hash_out.getResultId();
			long len = this._hash_out.getLength();
			LocalObject obj = _bank.get(id);
			this._object = obj;

			boolean save = true;
			if (obj.exists()) {
				save = false;
			}
			if (len != this._want_length) {
				save = false;
			}
			FinalOutput fout = this._final_out;
			if (save) {
				fout.saveTo(obj.getPath());
			} else {
				fout.drop();
			}
		}
	}

	class BuilderForZipped extends AbstractOutputStream implements
			LocalObjectBuilder {

		private final LocalObject _object;
		private OutputStream _master_out;
		private HashSUM _hash_out;
		private HeaderBuffer _hdr_buffer;

		public BuilderForZipped(LocalObject obj) {
			this._object = obj;
		}

		@Override
		public OutputStream openOutputStream(String xxx1, long xxx2) {
			HashAlgorithmProvider hap = _object.ownerBank()
					.getHashAlgorithmProvider();
			HashSUM hash = InnerFactory.newHashSum(hap);
			HeaderBuffer hdr = InnerFactory.newHeaderBuffer(_object);
			OutputStream plain2way = InnerFactory.new2WayOutput(
					hdr.getPlainOutput(), hash.getOutput());
			OutputStream unzip = InnerFactory.newUnzip(plain2way);
			OutputStream zip2way = InnerFactory.new2WayOutput(unzip,
					hdr.getZippedOutput());
			this._hash_out = hash;
			this._master_out = zip2way;
			this._hdr_buffer = hdr;
			return this;
		}

		@Override
		public LocalObject getResult() {
			return this._object;
		}

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			this._master_out.write(b, off, len);
		}

		@Override
		public void flush() throws IOException {
			this._master_out.flush();
		}

		@Override
		public void close() throws IOException {
			this._master_out.close();
			boolean save = true;
			if (this._object.exists()) {
				save = false;
			}
			HashID id1 = this._object.id();
			HashID id2 = this._hash_out.getResultId();
			if (!id1.equals(id2)) {
				save = false;
			}
			FinalOutput fout = this._hdr_buffer.getFinalOutput();
			if (save) {
				fout.saveTo(this._object.getPath());
			} else {
				fout.drop();
			}
		}
	}

	static class ImplHeaderBuffer extends AbstractOutputStream implements
			HeaderBuffer {

		private FinalOutput _final_out;
		private ByteArrayOutputStream _ba_out;
		private OutputStream _out;
		private final LocalObject _object;

		public ImplHeaderBuffer(LocalObject obj) {
			this._object = obj;
			this._ba_out = new ByteArrayOutputStream();
			this._out = this._ba_out;
		}

		public OutputStream getZippedOutput() {
			return this;
		}

		public OutputStream getPlainOutput() {
			return this.out_plain;
		}

		@Override
		public FinalOutput getFinalOutput() {
			return this._final_out;
		}

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			this._out.write(b, off, len);
		}

		@Override
		public void flush() {
		}

		@Override
		public void close() throws IOException {
			this._out.close();
		}

		final OutputStream out_plain = new AbstractOutputStream() {

			StringBuilder _sb = new StringBuilder();

			@Override
			protected void do_write(byte[] b, int off, int len)
					throws IOException {
				if (_sb == null)
					return;
				StringBuilder sb2 = null;
				final int end = off + len;
				for (int i = off; i < end; i++) {
					byte by = b[i];
					if (by == 0) {
						sb2 = _sb;
						_sb = null;
						break;
					} else {
						_sb.append((char) (by & 0x00ff));
					}
				}
				if (sb2 == null)
					return;
				int i = sb2.lastIndexOf(" ");
				String type = sb2.substring(0, i);
				String length = sb2.substring(i + 1);
				ImplHeaderBuffer.this.onHeader(type, Long.parseLong(length));
			}

			@Override
			public void flush() {
				// do nothing
			}

			@Override
			public void close() {
				// do nothing
			}
		};

		protected void onHeader(String type, long len) throws IOException {
			File tmp = this._object.ownerBank().newTempFile(this.toString());
			FinalOutput final_out = InnerFactory.newFinalOutput(tmp, len,
					this._object);
			byte[] ba = this._ba_out.toByteArray();
			OutputStream fout = final_out.getOutput();
			fout.write(ba);
			this._ba_out = null;
			this._final_out = final_out;
			this._out = fout;
		}

	}

	class ImplHashSumOutput extends AbstractOutputStream implements HashSUM {

		private final MessageDigest _md;
		private HashID _id = null;
		private long _length = 0;
		private boolean _closed = false;

		public ImplHashSumOutput(HashAlgorithmProvider hap) {
			this._md = hap.createMessageDigest();
		}

		@Override
		public OutputStream getOutput() {
			return this;
		}

		@Override
		public HashID getResultId() {
			return this._id;
		}

		@Override
		public long getLength() {
			return this._length;
		}

		@Override
		public void close() {
			this._closed = true;
			this._id = HashID.Factory.create(_md.digest());
		}

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			if (!_closed) {
				_md.update(b, off, len);
				_length += len;
			}
		}
	}

	static class SmallZippedOutputStream implements FinalOutput {

		private final ByteArrayOutputStream _baos;

		public SmallZippedOutputStream(File tmp) {
			this._baos = new ByteArrayOutputStream();
		}

		@Override
		public void saveTo(File dest) throws IOException {

			// System.out.println(this + ".save to " + dest);

			byte[] ba = _baos.toByteArray();
			File parent = dest.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(dest);
			out.write(ba);
			out.close();
		}

		@Override
		public void drop() {
			// System.out.println(this + ".drop " + this._baos.size() +
			// " bytes");
			this._baos.reset();
		}

		@Override
		public OutputStream getOutput() {
			return this._baos;
		}

	}

	static class LargeZippedOutputStream implements FinalOutput {

		private final File _tmp_file;
		private OutputStream _out;

		public LargeZippedOutputStream(File tmp) {
			this._tmp_file = tmp;
		}

		@Override
		public OutputStream getOutput() throws IOException {
			if (this._out == null) {
				File parent = this._tmp_file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				this._out = new FileOutputStream(this._tmp_file);
			}
			return this._out;
		}

		@Override
		public void saveTo(File dest) {

			// System.out.println(this + ".save to " + dest);

			if (this._tmp_file.exists()) {
				File parent = dest.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				this._tmp_file.renameTo(dest);
			}
		}

		@Override
		public void drop() {
			// System.out.println(this + ".drop " + this._tmp_file.length()
			// + " bytes");
			if (this._tmp_file.exists()) {
				this._tmp_file.delete();
			}
		}
	}

	static class NopZippedOutputStream implements FinalOutput {

		final OutputStream out = new OutputStream() {

			@Override
			public void write(int b) throws IOException {
			}

			@Override
			public void write(byte[] buf) throws IOException {
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

	static class Impl2WayOutputStream extends AbstractOutputStream {

		private final OutputStream _out1;
		private final OutputStream _out2;

		public Impl2WayOutputStream(OutputStream out1, OutputStream out2) {
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
