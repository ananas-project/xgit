package ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.HashID;

public interface LocalObjectPathGenerator {

	String gen(HashID id);

	File gen(HashID id, File base);

}
