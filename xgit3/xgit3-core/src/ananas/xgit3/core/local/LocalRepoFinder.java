package ananas.xgit3.core.local;

import java.io.File;

public interface LocalRepoFinder {

	LocalRepo findRepo(File file);

	LocalRepo findRepo(File file, int depthLimit);

	void findRepo(File file, int depthLimit, LocalRepoFinderHandler h);

	// direction
	String up = "up"; // child -> parent
	String down = "down"; // parent -> child

	void setDirection(String direction);

	String getDirection();

	String getTargetName();

	void setTargetName(String targetName);

	class Factory {

		public static LocalRepoFinder getFinder(String direction) {
			return ThisModule.getModule().newRepoFinder(direction);
		}

	}

}
