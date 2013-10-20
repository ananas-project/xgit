package ananas.impl.xgit.local;

import java.io.IOException;
import java.util.List;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.xgit.repo.local.ExtIndexBank;
import ananas.xgit.repo.local.ExtIndexInfo;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexBankImpl implements ExtIndexBank {

	private final LocalRepo _repo;
	private final VFile _dir;

	public ExtIndexBankImpl(LocalRepo repo, VFile bankDir) {
		this._repo = repo;
		this._dir = bankDir;
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public ExtIndexInfo get(VFile file) {

		String p0 = _repo.getWorkingDirectory().getDirectory()
				.getAbsolutePath();
		String p1 = file.getAbsolutePath();
		String offset = p1.substring(p0.length());
		VFileSystem vfs = _dir.getVFS();
		String sepa = vfs.separator();
		while (offset.startsWith(sepa)) {
			offset = offset.substring(1);
		}
		VFile meta = vfs.newFile(_dir, offset);
		return new ExtIndexInfoImpl(_repo, file, meta);
	}

	@Override
	public int scan(VFile dir, boolean r) throws IOException {

		if (dir == null) {
			dir = _repo.getWorkingDirectory().getDirectory();
		}

		Scanner scanner = new Scanner(this);
		return scanner.scan(dir, r, 64);

	}

	class Scanner {

		private final ExtIndexBank _bank;

		public Scanner(ExtIndexBank bank) {
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

}
