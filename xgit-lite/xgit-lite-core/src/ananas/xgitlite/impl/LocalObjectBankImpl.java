package ananas.xgitlite.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;

class LocalObjectBankImpl implements LocalObjectBank {

	private final File _dir;

	public LocalObjectBankImpl(File dir) {
		this._dir = dir;
	}

	@Override
	public LocalObject getObject(ObjectId id) {
		File file = this.__file_for_id(id);
		return new LocalObjectImpl(id, file);
	}

	private File __file_for_id(ObjectId id) {
		String str = id.toString();
		StringBuilder sb = new StringBuilder();
		final int i = 2;
		sb.append(str.substring(0, i));
		sb.append(File.separatorChar);
		sb.append(str.substring(i));
		return new File(_dir, sb.toString());
	}

	private static class Helper {

		private long _add_index = 1;
		private Charset _enc;

		private synchronized long __add_index() {
			return ((_add_index++) & 0xfffffffffffffffL);
		}

		public Charset encoding() {
			Charset enc = this._enc;
			if (enc == null) {
				enc = Charset.forName("UTF-8");
				_enc = enc;
			}
			return enc;
		}
	}

	private final static Helper helper = new Helper();

	private File __tmp_file(String type, long length) {

		final char sp = '_';
		final StringBuilder sb = new StringBuilder();

		sb.append("tmp");
		sb.append(File.separatorChar);
		sb.append(System.currentTimeMillis());
		sb.append(sp);
		sb.append((type == null) ? "notype" : type);
		sb.append(sp);
		sb.append(length);
		sb.append(sp);
		sb.append(this.hashCode() & 0x7fffffff);
		sb.append(sp);
		sb.append(helper.__add_index());
		sb.append(".tmp");

		return new File(_dir, sb.toString());
	}

	private LocalObject __addObject(String type, long length, InputStream in)
			throws IOException, XGLException {

		File tmp_file = this.__tmp_file(type, length);
		ObjectFileBuilder writer = new ObjectFileBuilder(tmp_file);
		writer.open(type, length);
		byte[] buff = new byte[1024];
		for (;;) {
			int cb = in.read(buff);
			if (cb < 0) {
				break;
			}
			writer.write(buff, 0, cb);
		}
		writer.close();
		return writer.getObject();
	}

	@Override
	public LocalObject addObject(String type, long length, InputStream in)
			throws IOException, XGLException {
		return this.__addObject(type, length, in);
	}

	@Override
	public LocalObject addObject(String type, File file) throws IOException,
			XGLException {
		final long len = file.length();
		InputStream in = new FileInputStream(file);
		LocalObject obj = this.__addObject(type, len, in);
		in.close();
		return obj;
	}

	class ObjectFileBuilder {

		private final File _tmp_file;
		private long _body_length;
		private long _body_cb;
		private LocalObject _object;
		private MessageDigest _sha1;
		private OutputStream _out;
		private BufferedFileOutputStream _out_bfos;

		public ObjectFileBuilder(File tmp_file) {
			this._tmp_file = tmp_file;
		}

		public LocalObject getObject() {
			return this._object;
		}

		public void open(String type, long length) throws IOException {

			if (type == null) {
				type = "null";
			}
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				this._sha1 = md;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			BufferedFileOutputStream bfos = new BufferedFileOutputStream(
					this._tmp_file);
			this._out = new java.util.zip.DeflaterOutputStream(bfos);
			this._out_bfos = bfos;

			// build head
			StringBuilder sb = new StringBuilder();
			sb.append(type);
			sb.append(' ');
			sb.append(length);
			sb.append((char) 0);
			byte[] hdr = sb.toString().getBytes(helper.encoding());
			this.write(hdr, 0, hdr.length);

			// init
			this._body_length = length;
			this._body_cb = 0;
		}

		public void close() throws IOException, XGLException {

			_out.flush();
			_out.close();

			if (this._body_cb != this._body_length) {
				throw new XGLException("the want/real length not match: "
						+ _body_length + "/" + _body_cb);
			}

			byte[] ba = _sha1.digest();
			ObjectId id = XGitLite.getInstance().createObjectId(ba);
			final File final_file = LocalObjectBankImpl.this.__file_for_id(id);
			LocalObject obj = new LocalObjectImpl(id, final_file);
			if (obj.exists()) {
				// remove tmp file
				this._out_bfos.finish(null);
			} else {
				// move tmp file
				this._out_bfos.finish(final_file);
			}
			this._object = obj;
		}

		public void write(byte[] buff, int offset, int length)
				throws IOException {
			_sha1.update(buff, offset, length);
			_out.write(buff, offset, length);
			_body_cb += length;
		}
	}

	static class BufferedFileOutputStream extends OutputStream {

		private final long _key_value;
		private final File _tmp_file;
		private ByteArrayOutputStream _baos;
		private OutputStream _out;

		public BufferedFileOutputStream(File file) {
			this._tmp_file = file;
			this._baos = new ByteArrayOutputStream(1024 * 4);
			this._key_value = 1024 * 64;
			this._out = _baos;
		}

		public void finish(final File mv2) throws IOException, XGLException {
			_out.close();
			final File tmp = _tmp_file;
			if (mv2 == null) {
				// remove
				if (tmp.exists()) {
					tmp.delete();
				}
			} else {
				// move
				if (!tmp.exists()) {
					tmp.getParentFile().mkdirs();
					OutputStream out = new FileOutputStream(tmp);
					out.write(_baos.toByteArray());
					out.close();
				}
				mv2.getParentFile().mkdirs();
				if (!tmp.renameTo(mv2)) {
					throw new XGLException("cannot move " + tmp + " to " + mv2);
				}
			}
			this._out = null;
			this._baos = null;
		}

		private void __check() throws IOException {
			final ByteArrayOutputStream baos = _baos;
			if (baos == null)
				return;
			if (baos.size() >= this._key_value) {
				_baos = null;
				File file = _tmp_file;
				if (!file.exists()) {
					file.getParentFile().mkdirs();
				}
				FileOutputStream out = new FileOutputStream(file);
				out.write(baos.toByteArray());
				_out = out;
			}
		}

		@Override
		public void write(int b) throws IOException {
			_out.write(b);
			this.__check();
		}

		@Override
		public void write(byte[] buff) throws IOException {
			_out.write(buff);
			this.__check();
		}

		@Override
		public void write(byte[] buff, int off, int len) throws IOException {
			_out.write(buff, off, len);
			this.__check();
		}

		@Override
		public void flush() throws IOException {
			_out.flush();
		}

		@Override
		public void close() throws IOException {
			// do nothing
		}

	}

}
