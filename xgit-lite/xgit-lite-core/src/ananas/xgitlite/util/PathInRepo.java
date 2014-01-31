package ananas.xgitlite.util;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.XGLException;
import ananas.xgitlite.local.LocalRepo;

public class PathInRepo {

	private final String _path;
	private final LocalRepo _repo;

	public PathInRepo(String path, LocalRepo repo) {
		this._repo = repo;
		this._path = path;
	}

	public static PathInRepo newInstance(LocalRepo repo, File path)
			throws IOException, XGLException {

		File wkdir = repo.getWorkingDirectory();
		String pir = Helper.getOffsetPath(wkdir, path);
		return new PathInRepo(pir, repo);
	}

	public String getPath() {
		return _path;
	}

	public LocalRepo getRepo() {
		return _repo;
	}

	public String toString() {
		return _path;
	}

}
