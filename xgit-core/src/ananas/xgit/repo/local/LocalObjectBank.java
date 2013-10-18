package ananas.xgit.repo.local;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectBank;
import ananas.xgit.repo.ObjectId;

public interface LocalObjectBank extends ObjectBank {

	VFile getBankDirectory();

	LocalObject getObject(ObjectId id);

	/**
	 * @param in
	 *            the raw input stream
	 * */
	LocalObject addObject(String type, long length, InputStream in)
			throws IOException;
}
