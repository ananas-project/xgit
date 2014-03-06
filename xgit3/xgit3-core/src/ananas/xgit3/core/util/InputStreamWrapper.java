package ananas.xgit3.core.util;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends AbstractInputStream {

	private final InputStream target;

	InputStreamWrapper(InputStream in) {
		this.target = in;
	}

	@Override
	protected int do_read(byte[] buf, int off, int len) throws IOException {
		return target.read(buf, off, len);
	}

	public int available() throws IOException {
		return target.available();
	}

	public void close() throws IOException {
		target.close();
	}

	public void mark(int readlimit) {
		target.mark(readlimit);
	}

	public boolean markSupported() {
		return target.markSupported();
	}

	public void reset() throws IOException {
		target.reset();
	}

	public long skip(long n) throws IOException {
		return target.skip(n);
	}

}
