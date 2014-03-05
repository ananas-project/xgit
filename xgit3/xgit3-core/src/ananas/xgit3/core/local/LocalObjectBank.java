package ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;

public interface LocalObjectBank   extends   DirectoryNode  {

	LocalObject get(HashID id);

}
