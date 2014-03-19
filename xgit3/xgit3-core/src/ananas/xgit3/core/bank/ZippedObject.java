package ananas.xgit3.core.bank;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public interface ZippedObject {

	boolean exists();

	ObjectBank ownerBank();

	InputStream openZippedInputStream() throws IOException;

	OutputStream openZippedOutputStream() throws IOException;

	HashID id();

	String type();

	long length();

}
