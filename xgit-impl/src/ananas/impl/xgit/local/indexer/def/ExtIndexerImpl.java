package ananas.impl.xgit.local.indexer.def;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.XGitException;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexerImpl implements Indexer {

	private final LocalRepo _repo;
	private final VFile _db_file;
	private final String _wk_path;
	private final String _rp_path;

	public ExtIndexerImpl(LocalRepo repo, VFile indexFile) {
		this._repo = repo;
		this._db_file = indexFile;
		this._wk_path = this.__can_path(repo.getWorkingDirectory()
				.getDirectory());
		this._rp_path = this.__can_path(repo.getRepoDirectory());
	}

	private String __can_path(VFile path) {
		try {
			return path.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public VFile getIndexDB() {
		return this._db_file;
	}

	@Override
	public IndexNode getNode(VFile file) throws XGitException {
		String pathCan = null;
		try {
			pathCan = file.getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		{
			String pathAbs = file.getAbsolutePath();
			if (!pathAbs.equals(pathCan)) {
				VFileSystem vfs = file.getVFS();
				file = vfs.newFile(pathCan);
			}
		}
		if (!__is_working_file(pathCan)) {
			throw new XGitException("the file is not a working node : " + file);
		}
		String offset = __get_offset(pathCan);
		return new ExtIndexNodeImpl(this, file, offset);
	}

	private String __get_offset(String path) {
		final int l1, l2;
		l1 = this._wk_path.length();
		l2 = path.length();
		if (l1 < l2)
			return path.substring(l1 + 1);
		else
			return "";
	}

	private boolean __is_working_file(String path) {
		if (!path.startsWith(this._wk_path)) {
			return false;
		}
		if (path.startsWith(this._rp_path)) {
			return false;
		}
		return true;
	}

	@Override
	public void add(IndexNode node, boolean r) throws IOException,
			XGitException {
		VFile tar = node.getTargetFile();
		if (r) {
			this.__add_r(tar, 0);
		} else {
			node.add();
		}
	}

	interface Const {

		int add_depth_limit = 48;
	}

	private void __add_r(VFile tar, int depth) throws IOException,
			XGitException {

		String name = tar.getName();
		if (".".equals(name)) {
			return;
		} else if ("..".equals(name)) {
			return;
		} else if (".git".equalsIgnoreCase(name)) {
			return;
		}

		if (depth > Const.add_depth_limit) {
			throw new RuntimeException("the path is too deep : " + tar);
		}

		if (tar.isDirectory()) {
			VFile[] chs = tar.listFiles();
			for (VFile ch : chs) {

				this.__add_r(ch, depth + 1);
			}
		}

		IndexNode node = this.getNode(tar);
		node.add();

	}

}
