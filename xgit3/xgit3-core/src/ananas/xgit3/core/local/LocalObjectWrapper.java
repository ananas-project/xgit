package ananas.xgit3.core.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public class LocalObjectWrapper implements LocalObject {

	private final LocalObject target;

	public LocalObjectWrapper(LocalObject _target) {
		this.target = _target;
	}

	public File getPath() {
		return target.getPath();
	}

	public InputStream openPlainInputStream() throws IOException {
		return target.openPlainInputStream();
	}

	public InputStream openZippedInputStream() throws IOException {
		return target.openZippedInputStream();
	}

	public OutputStream openZippedOutputStream() throws IOException {
		return target.openZippedOutputStream();
	}

	public LocalObjectBank ownerBank() {
		return target.ownerBank();
	}

	public long length() {
		return target.length();
	}

	public String type() {
		return target.type();
	}

	public HashID id() {
		return target.id();
	}

	public boolean exists() {
		return target.exists();
	}

}
