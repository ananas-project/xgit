package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ananas.xgit.repo.DefaultObjectId;
import ananas.xgit.repo.ObjectId;

public class HashObjectIdOutputStream extends OutputStream {

	private final MessageDigest hash;
	private byte[] result;

	public HashObjectIdOutputStream() {
		MessageDigest _hash = null;
		try {
			_hash = java.security.MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.hash = _hash;
	}

	@Override
	public void write(int b) throws IOException {
		hash.update((byte) b);
	}

	@Override
	public void write(byte[] buffer, int offset, int length) throws IOException {
		hash.update(buffer, offset, length);
	}

	@Override
	public void close() {
		this.result = hash.digest();
	}

	public ObjectId genId() {
		return new DefaultObjectId(this.result);
	}

}
