package ananas.xgitlite;

import java.io.File;
import java.io.IOException;

public interface LocalRepo extends Repo {

	interface Name {

		String xgit_conf = "xgit.config";
		String xgit_dir = "xgit";

		String branches = "branches";
		String hooks = "hooks";
		String info = "info";
		String logs = "logs";
		String objects = "objects";
		String refs = "refs";

	}

	LocalRepoConfig getConfig();

	LocalObject getObject(ObjectId id);

	File getWorkingDirectory();

	File getRepoDirectory();

	void init(boolean bare) throws XGLException, IOException;

}
