package ananas.xgit.repo.local;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;

public interface ExtIndexBank {

	LocalRepo getRepo();

	int scan(VFile dir, boolean r) throws IOException;

	ExtIndexInfo get(VFile file);

}
