package ananas.lib.xgit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamPump {

	private final InputStream in;
	private final OutputStream out;
	private final byte[] buff;

	public StreamPump(InputStream _in, OutputStream _out) {
		in = _in;
		out = _out;
		buff = new byte[512];
	}

	public void run() throws IOException {

		for (int cb = in.read(buff); cb >= 0; cb = in.read(buff)) {
			out.write(buff, 0, cb);
		}
	}

}
