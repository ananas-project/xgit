package ananas.xgitlite.local;

import java.io.File;

public interface LocalRepoFinder {

	interface Listener {

		void onFind(LocalRepo repo);

		void onDirectory(File path);
	}

	LocalRepo[] find(File path, Listener listener);

}
