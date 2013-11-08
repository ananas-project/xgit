package ananas.impl.xgit.local.indexer.def;

import java.io.IOException;
import java.util.List;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexerImpl implements Indexer {

	private final LocalRepo _repo;
	private final VFile _index_file;
	private final ThePath _meta_base;
	private final ThePath _tar_base;

	public ExtIndexerImpl(LocalRepo repo, VFile indexFile) {
		this._repo = repo;
		this._index_file = indexFile;

		this._meta_base = ThePath.getPath(indexFile);
		this._tar_base = ThePath.getPath(repo.getWorkingDirectory()
				.getDirectory());
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public IndexNode get(VFile file) {
		VFile meta = this.__get_meta_path(file);
		if (meta == null) {
			return null;
		}
		return new ExtIndexNodeImpl(this, file, meta);
	}

	@Override
	public int scan(VFile dir, boolean r) throws IOException {
		if (dir == null) {
			dir = _repo.getWorkingDirectory().getDirectory();
		}
		ScannerReflex scanRef = new ScannerReflex(this);
		Scanner scanner = new Scanner(this);
		VFile rdir = this.__get_meta_path(dir);
		scanRef.scan(rdir, r, 64);
		return scanner.scan(dir, r, 64);
	}

	private VFile __get_meta_path(VFile target) {

		ThePath full = ThePath.getPath(target);
		ThePath base = this._tar_base;

		String offset = ThePath.getOffset(base, full);

		// TODO ...
		// this._index_file.get
		return null;
	}

	static class ThePath {

		static ThePath getPath(VFile path) {
			// TODO ...
			return null;
		}

		public static String getOffset(ThePath base, ThePath full) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class ScannerReflex {

		private final Indexer _indexer;

		public ScannerReflex(Indexer indexer) {
			this._indexer = indexer;
		}

		public void scan(VFile path, boolean r, int deep) {
			if (!path.exists()) {
				return;
			}
			if (deep < 0) {
				throw new RuntimeException("too deep : " + path);
			}
			if (path.isDirectory()) {
				List<VFile> chs = path.listFiles();
				for (VFile ch : chs) {
					this.scan(ch, r, deep - 1);
				}
			}
			VFile tar = this.__target_of(path);
			if (tar.exists()) {
				if (tar.isDirectory() != path.isDirectory()) {
					path.delete();
				}
			} else {
				path.delete();
			}
		}

		private VFile __target_of(VFile path) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class Scanner {

		private final Indexer _bank;

		public Scanner(Indexer bank) {
			_bank = bank;
		}

		public int scan(VFile dir, boolean r, int depthLimit)
				throws IOException {
			int count = 0;
			String name = dir.getName();
			if (name.equals(".git")) {
				return count;
			} else if (name.equals(".")) {
				return count;
			} else if (name.equals("..")) {
				return count;
			}
			List<VFile> list = dir.listFiles();
			for (VFile ch : list) {
				if (ch.isDirectory()) {
					if (r) {
						count += this.scan(ch, r, depthLimit - 1);
					}
				} else {
					_bank.get(ch).add();
					count++;
				}
			}
			return count;
		}
	}

	@Override
	public VFile getIndexFile() {
		return this._index_file;
	}

}
