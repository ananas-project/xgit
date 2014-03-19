package ananas.xgit3.core.bank;

import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public interface ZippedObject {

	boolean exists();

	ObjectBank ownerBank();

	InputStream openZippedInputStream();

	OutputStream openZippedOutputStream();

	HashID id();

	String type();

	long length();

}
