package impl.ananas.xgit3.core.local;

import impl.ananas.xgit3.core.local.tree.DefaultTreeMaker;

import java.io.File;

import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.ext.XGitExtends;
import ananas.xgit3.core.local.tree.TreeMaker;

public class DefaultLocalRepo implements LocalRepo {

	private final File _path;
	private LocalObjectBank _bank;
	private XGitExtends _dir_xgit;

	public DefaultLocalRepo(File path) {
		this._path = path;
	}

	@Override
	public File getPath() {
		return this._path;
	}

	@Override
	public LocalObjectBank getBank() {
		LocalObjectBank bank = this._bank;
		if (bank == null) {
			File path = new File(this._path, "objects");
			bank = new DefaultLocalBank(path, null, null, null);
			this._bank = bank;
		}
		return bank;
	}

	@Override
	public XGitExtends getXGitExtends() {
		XGitExtends ext = this._dir_xgit;
		if (ext == null) {
			File dir = new File(this._path, "xgit");
			ext = new XGitExtendsImpl(dir);
			this._dir_xgit = ext;
		}
		return ext;
	}

	@Override
	public TreeMaker getTreeMaker() {
		return new DefaultTreeMaker();
	}

}
