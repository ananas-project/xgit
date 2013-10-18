package ananas.xgit.repo.local;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.XGitObject;

public interface LocalObject extends XGitObject {

	LocalObjectBank getBank();

	InputStream openRawInputStream() throws IOException;

	// OutputStream openRawOutputStream();

	VFile getZipFile();

}
