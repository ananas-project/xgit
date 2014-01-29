package ananas.xgitlite;

import java.io.File;
import java.io.IOException;

public interface LocalRepoConfig {

	interface Element {

		String getType();

		String getName();

		void set(String key, String value);

		String get(String key);

	}

	interface Core extends Element {

		String type = "core";

		String filemode = "filemode";
		String bare = "bare";
		String xgit = "xgit";
		String repositoryformatversion = "repositoryformatversion";
	}

	interface Remote extends Element {
		String type = "remote";
	}

	interface Branch extends Element {
		String type = "branch";
	}

	Core getCore();

	Remote[] listRemotes();

	Branch[] listBranches();

	Element[] listElements();

	void save(File file) throws IOException;

	void load(File file) throws IOException;

}
