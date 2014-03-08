package ananas.xgit3.core.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashID;

public interface LocalObject extends FileNode {

	int mode_file = 100644; // 表明这是一个普通文件
	int mode_exe = 100755; // 表示可执行文件
	int mode_link = 120000; // 表示符号链接
	int mode_dir = 40000; // dir

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
