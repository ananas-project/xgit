package ananas.lib.xgit;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.util.SHA1Value;

public interface XGitObject {

	String getType();

	long getLength();

	SHA1Value getHash();

	InputStream openZipStream() throws IOException;

	InputStream openRawStream() throws IOException;

}
