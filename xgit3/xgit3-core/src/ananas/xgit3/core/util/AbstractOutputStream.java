package ananas.xgit3.core.util;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractOutputStream extends OutputStream {

	private byte[] _1b;

	@Override
	public final void write(int b) throws IOException {
		byte[] buf = _1b;
		if (buf == null) {
			buf = new byte[1];
			_1b = buf;
		}
		buf[0] = (byte) b;
		this.do_write(buf, 0, 1);
	}

	@Override
	public final void write(byte[] b, int off, int len) throws IOException {
		this.do_write(b, off, len);
	}

	@Override
	public final void write(byte[] b) throws IOException {
		this.do_write(b, 0, b.length);
	}

	protected abstract void do_write(byte[] b, int off, int len)
			throws IOException;

}
