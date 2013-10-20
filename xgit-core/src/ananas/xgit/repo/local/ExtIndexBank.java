package ananas.xgit.repo.local;

import ananas.lib.io.vfs.VFile;

public interface ExtIndexBank {

	LocalRepo getRepo();

	int scan(VFile dir, boolean r);

	ExtIndexInfo get(VFile file);

}
