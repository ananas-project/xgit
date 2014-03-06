package ananas.xgit3.core.util;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWrapper extends AbstractOutputStream {

	private final OutputStream target;

	public OutputStreamWrapper(OutputStream out) {
		this.target = out;
	}

	@Override
	protected void do_write(byte[] b, int off, int len) throws IOException {
		target.write(b, off, len);
	}

	public void close() throws IOException {
		target.close();
	}

	public void flush() throws IOException {
		target.flush();
	}

}
