package ananas.xgit3.core.util;

import java.io.IOException;
import java.security.MessageDigest;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;

public class HashOutputStream extends AbstractOutputStream {

	private final MessageDigest _md;
	private HashID _id;

	public HashOutputStream(HashAlgorithmProvider hap) {
		this._md = hap.createMessageDigest();
	}

	@Override
	protected void do_write(byte[] b, int off, int len) throws IOException {
		_md.update(b, off, len);
	}

	@Override
	public void close() throws IOException {
		byte[] ba = _md.digest();
		this._id = HashID.Factory.create(ba);
	}

	@Override
	public void flush() throws IOException {
	}

	public HashID getResultId() {
		return _id;
	}
}
