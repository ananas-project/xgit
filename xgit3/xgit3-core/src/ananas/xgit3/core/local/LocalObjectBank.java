package ananas.xgit3.core.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ananas.xgit3.core.HashID;

public interface LocalObjectBank extends DirectoryNode {

	LocalObject get(HashID id);

	LocalObject add(String type, long length, InputStream in)
			throws IOException;

	LocalObject add(String type, File file) throws IOException;

	File getTempDirectory();

	File newTempFile(String ref_name);

}
