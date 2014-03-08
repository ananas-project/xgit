package impl.ananas.xgit3.core.local.tree;

import java.io.File;

public interface TreeNodeHandler {

	void onWalkBegin(File root);

	void onDirBegin(File root, File dir);

	void onFile(File root, File file);

	void onDirEnd(File root, File dir);

	void onWalkEnd(File root);

}
