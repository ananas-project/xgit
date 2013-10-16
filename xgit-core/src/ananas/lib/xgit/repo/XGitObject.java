package ananas.lib.xgit.repo;

import java.io.InputStream;
import java.io.OutputStream;

public interface XGitObject {

	ObjectBank getBank();

	ObjectId id();

	boolean exists();

	String type();

	long length();

	InputStream openInputStream();

	OutputStream openOutputStream();

}
