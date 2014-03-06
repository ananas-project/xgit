package ananas.xgit3.core.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public interface LocalObject extends FileNode {

	String blob = "blob";
	String tree = "tree";
	String commit = "commit";
	String tag = "tag";

	InputStream openPlainInputStream() throws IOException;

	InputStream openZippedInputStream() throws IOException;

	// OutputStream openPlainOutputStream() throws IOException;

	OutputStream openZippedOutputStream() throws IOException;

	LocalObjectBank ownerBank();

	long length();

	String type();

	HashID id();

	boolean exists();

}
