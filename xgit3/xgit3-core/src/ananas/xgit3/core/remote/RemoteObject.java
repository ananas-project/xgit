package ananas.xgit3.core.remote;

import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public interface RemoteObject {

	HashID id();

	String type();

	long length();

	boolean exists();

	InputStream openZippedInputStream();

	OutputStream openZippedOutputStream();

}
