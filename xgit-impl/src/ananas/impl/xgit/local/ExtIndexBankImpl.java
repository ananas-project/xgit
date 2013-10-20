package ananas.impl.xgit.local;

import java.util.List;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.local.ExtIndexBank;
import ananas.xgit.repo.local.ExtIndexInfo;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexBankImpl implements ExtIndexBank {

	private final LocalRepo _repo;

	public ExtIndexBankImpl(LocalRepo repo) {
		this._repo = repo;
	}

	@Override
	public LocalRepo getRepo() {
		return this._repo;
	}

	@Override
	public ExtIndexInfo get(VFile file) {
		return new ExtIndexInfoImpl(file);
	}

	@Override
	public int scan(VFile dir, boolean r) {

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

		public int scan(VFile dir, boolean r, int depthLimit) {

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
