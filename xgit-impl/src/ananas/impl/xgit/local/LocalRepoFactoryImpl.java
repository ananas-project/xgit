package ananas.impl.xgit.local;

import java.net.URI;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.Repo;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;

public class LocalRepoFactoryImpl implements LocalRepoFactory {

	@Override
	public Repo createRepo(URI uri) {
		VFileSystem vfs = VFS.getDefaultFactory().defaultFileSystem()
				.getFactory().defaultFileSystem();
		return this.createRepo(vfs.newFile(uri));
	}

	@Override
	public LocalRepo createRepo(VFile file) {
		return this.createRepo(file, false);
	}

	@Override
	public LocalRepo createRepo(VFile file, boolean bare) {
		LocalRepo repo = new LocalRepoImpl(file, bare);
		return repo;
	}

}
