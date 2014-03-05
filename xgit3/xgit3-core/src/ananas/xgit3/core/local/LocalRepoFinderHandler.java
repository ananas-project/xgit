package ananas.xgit3.core.local;

import java.io.File;

public interface LocalRepoFinderHandler {

	void begin();

	void findTarget(File path);

	boolean keepFinding();

	void findInDirectory(File path);

	void end();

}
