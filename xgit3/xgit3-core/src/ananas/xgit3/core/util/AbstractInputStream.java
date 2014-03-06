package ananas.xgit3.core.util;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractInputStream extends InputStream {

	private byte[] _1b;

	protected abstract int do_read(byte[] buf, int off, int len)
			throws IOException;

	@Override
	public final int read() throws IOException {
		byte[] buf = _1b;
		if (buf == null) {
			buf = new byte[1];
			_1b = buf;
		}
		int cb = this.do_read(buf, 0, 1);
		if (cb == 1)
			return buf[0];
		else
			return -1;
	}

	@Override
	public final int read(byte[] buf, int off, int len) throws IOException {
		return this.do_read(buf, off, len);
	}

	@Override
	public final int read(byte[] buf) throws IOException {
		return this.do_read(buf, 0, buf.length);
	}

}
