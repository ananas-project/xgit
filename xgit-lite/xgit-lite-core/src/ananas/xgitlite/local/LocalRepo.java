package ananas.xgitlite.local;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;
import ananas.xgitlite.XGLException;

public interface LocalRepo extends Repo {

	interface Name {

		String xgit_conf = "xgit/config";
		String xgit_dir = "xgit";

		String branches = "branches";
		String hooks = "hooks";
		String info = "info";
		String logs = "logs";
		String objects = "objects";
		String refs = "refs";

	}

	LocalRepoConfig getConfig();

	LocalObjectBank getObjectBank();

	File getWorkingDirectory();

	File getRepoDirectory();

	void init(boolean bare) throws XGLException, IOException;

	void check() throws XGLException, IOException;

	void add(File path);

	void commit(File path);
}
