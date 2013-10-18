package ananas.xgit.repo;

import java.io.InputStream;
import java.io.OutputStream;

public interface XGitObject {

	ObjectBank getBank();

	ObjectId id();

	boolean exists();

	String type();

	long length();

	/**
	 * @return the zip input stream
	 * */
	InputStream openInputStream();

	/**
	 * @return the zip output stream
	 * */
	OutputStream openOutputStream();

}
