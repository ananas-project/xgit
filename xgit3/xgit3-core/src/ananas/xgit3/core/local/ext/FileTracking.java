package ananas.xgit3.core.local.ext;

import java.io.File;

import ananas.xgit3.core.HashID;

public interface FileTracking {

	File getPath();

	long length();

	HashID id();

	long lastModified();

	void doTrack(HashID id, long lastMod, long length);

	boolean isTracked();

}
