package test.ananas.lib.xgit;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;

public class TestObjectManager {

	public static void main(String[] args) {

		VFileSystem vfs = VFS.getFactory().defaultFileSystem();
		VFile file = vfs.newFile("/tmp/test/xgit/.xgit");

	}
}
