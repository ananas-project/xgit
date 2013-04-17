package ananas.lib.xgit.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import ananas.lib.io.Connector;
import ananas.lib.io.InputConnection;
import ananas.lib.io.vfs.VFile;
import ananas.lib.util.SHA1Value;
import ananas.lib.xgit.XGitObject;

class XGitObjectImpl implements XGitObject {

	private final SHA1Value mHash;
	private final VFile mFile;
	private MyRawStream mRS0;

	public XGitObjectImpl(SHA1Value hash, VFile file) {
		this.mHash = hash;
		this.mFile = file;
	}

	@Override
	public String getType() {
		MyRawStream rs = this._getRawStream0();
		return rs.getType();
	}

	@Override
	public long getLength() {
		MyRawStream rs = this._getRawStream0();
		return rs.getLength();
	}

	private MyRawStream _getRawStream0() {
		MyRawStream ret = this.mRS0;
		if (ret == null) {
			ret = new MyRawStream();
			try {
				ret.open();
				ret.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.mRS0 = ret;
		}
		return ret;
	}

	@Override
	public SHA1Value getHash() {
		return this.mHash;
	}

	@Override
	public InputStream openZipStream() throws IOException {
		MyZipStream ret = new MyZipStream();
		ret.open();
		return ret;
	}

	@Override
	public InputStream openRawStream() throws IOException {
		MyRawStream ret = new MyRawStream();
		ret.open();
		return ret;
	}

	class MyRawStream extends InputStream {

		private MyZipStream mZipStream;
		private InflaterInputStream mIIS;

		@Override
		public int read() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		public void open() throws IOException {
			MyZipStream zstr = new MyZipStream();
			zstr.open();
			this.mZipStream = zstr;

			StringBuilder sb = new StringBuilder();
			InflaterInputStream iis = new InflaterInputStream(zstr);
			for (int b = iis.read(); b > 0; b = iis.read()) {
				sb.append((char) b);
				if (sb.length() > 64) {
					throw new RuntimeException(
							"the length of object zip file header is too long.");
				}
			}
			System.out.println("" + sb);

			this.mIIS = iis;

		}

		public String getType() {
			// TODO Auto-generated method stub
			return null;
		}

		public long getLength() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	class MyZipStream extends InputStream {

		private InputConnection mConn;
		private InputStream mIS;

		public int available() throws IOException {
			return mIS.available();
		}

		public void close() throws IOException {
			mIS.close();
			this.mConn.close();
		}

		public void mark(int readlimit) {
			mIS.mark(readlimit);
		}

		public boolean markSupported() {
			return mIS.markSupported();
		}

		public int read() throws IOException {
			return mIS.read();
		}

		public int read(byte[] arg0, int arg1, int arg2) throws IOException {
			return mIS.read(arg0, arg1, arg2);
		}

		public int read(byte[] b) throws IOException {
			return mIS.read(b);
		}

		public void reset() throws IOException {
			mIS.reset();
		}

		public long skip(long arg0) throws IOException {
			return mIS.skip(arg0);
		}

		public void open() throws IOException {
			VFile file = XGitObjectImpl.this.mFile;
			InputConnection conn = (InputConnection) Connector.Factory
					.getConnector().open(file.getURI());
			this.mConn = conn;
			InputStream is = conn.getInputStream();
			this.mIS = is;
		}

	}

}
