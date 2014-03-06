package impl.ananas.xgit3.core.local;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.DeflaterOutputStream;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.util.AbstractOutputStream;
import ananas.xgit3.core.util.HashOutputStream;

public class LocalObjectBuilder {

	// private final File _tmp_file;
	private final long _length;
	private final String _type;
	private final Output _final_out;
	private final HashAlgorithmProvider _hash_provider;
	private OutputStream _final_out_stream;
	private HashOutputStream _hash_out_stream;
	// //
	private Exception _error;
	private HashID _id;
	private long _count_content_byte;

	public LocalObjectBuilder(String type, long length, File tmpFile,
			HashAlgorithmProvider hap) {
		this._hash_provider = hap;
		this._type = type;
		this._length = length;
		// this._tmp_file = tmpFile;
		if (length < (128 * 1024)) {
			this._final_out = new SmallOutput();
		} else {
			this._final_out = new BigOutput(tmpFile);
		}

	}

	public void end() {
		try {
			OutputStream out = this.getOutputStream();
			out.flush();
			out.close();
			if (this._count_content_byte != this._length) {
				System.err.println("want:" + this._length);
				System.err.println("real:" + this._count_content_byte);
				throw new RuntimeException("the object length is not match!");
			}

			this._id = this._hash_out_stream.getResultId();

		} catch (Exception e) {
			this._error = e;
		}
	}

	public void begin() {
		try {

			this._hash_out_stream = new HashOutputStream(this._hash_provider);
			this._final_out_stream = new DeflaterOutputStream(
					_final_out.getOutputStream());

			OutputStream out = this.getOutputStream();
			Writer wtr = new OutputStreamWriter(out);
			wtr.append(this._type);
			wtr.append(' ');
			wtr.append(Long.toString(this._length));
			wtr.flush();
			out.write(0);
			out.flush();
			this._count_content_byte = 0;
		} catch (Exception e) {
			this._error = e;
		}
	}

	public void loadData(InputStream in) {
		try {
			OutputStream out = this.getOutputStream();
			byte[] buf = new byte[512];
			long cbt = 0;
			for (;;) {
				int cb = in.read(buf);
				if (cb < 0)
					break;
				out.write(buf, 0, cb);
				cbt += cb;
				if (this._length < cbt) {
					throw new RuntimeException("out of length");
				}
			}
			out.flush();
			this._count_content_byte += cbt;
		} catch (Exception e) {
			this._error = e;
		}
	}

	private OutputStream getOutputStream() {
		return this._master_out;
	}

	private final OutputStream _master_out = new AbstractOutputStream() {

		@Override
		protected void do_write(byte[] b, int off, int len) throws IOException {
			LocalObjectBuilder.this._hash_out_stream.write(b, off, len);
			LocalObjectBuilder.this._final_out_stream.write(b, off, len);
		}

		@Override
		public void close() throws IOException {
			LocalObjectBuilder.this._hash_out_stream.close();
			LocalObjectBuilder.this._final_out_stream.close();
		}

		@Override
		public void flush() throws IOException {
			LocalObjectBuilder.this._hash_out_stream.flush();
			LocalObjectBuilder.this._final_out_stream.flush();
		}
	};

	public HashID getId() {
		return this._id;
	}

	public Throwable getError() {
		return this._error;
	}

	private Output getOutput() {
		return this._final_out;
	}

	interface Output {

		OutputStream getOutputStream() throws IOException;

		void moveTo(File dest) throws IOException;

		void drop();

	}

	private static class BigOutput implements Output {

		private final File _tmp;
		private OutputStream _out;

		public BigOutput(File tmp) {
			this._tmp = tmp;
		}

		@Override
		public OutputStream getOutputStream() throws FileNotFoundException {
			OutputStream out = this._out;
			if (out == null) {
				File parent = _tmp.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				out = new FileOutputStream(_tmp);
				this._out = out;
			}
			return out;
		}

		@Override
		public void moveTo(File dest) {
			if (_tmp.exists()) {
				File parent = dest.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				_tmp.renameTo(dest);
			}
		}

		@Override
		public void drop() {
			if (_tmp.exists()) {
				_tmp.delete();
			}
		}
	}

	private static class SmallOutput implements Output {

		private final ByteArrayOutputStream _baos;
		private final OutputStream _out;

		public SmallOutput() {
			this._baos = new ByteArrayOutputStream();
			this._out = _baos;
		}

		@Override
		public OutputStream getOutputStream() {
			return _out;
		}

		@Override
		public void moveTo(File dest) throws IOException {
			File parent = dest.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(dest);
			out.write(_baos.toByteArray());
			out.close();
		}

		@Override
		public void drop() {
			this._baos.reset();
		}
	}

	public void moveTo(File dest) {
		try {
			this.getOutput().moveTo(dest);
		} catch (IOException e) {
			this._error = e;
		}
	}

	public void drop() {
		this.getOutput().drop();
	}

}
