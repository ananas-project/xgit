package impl.ananas.xgit3.core.local;

import impl.ananas.xgit3.core.local.tree.DefaultTreeMaker;

import java.io.File;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalBlob;
import ananas.xgit3.core.local.LocalCommit;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalObjectPathGenerator;
import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.LocalTag;
import ananas.xgit3.core.local.LocalTree;
import ananas.xgit3.core.local.ext.XGitExtends;
import ananas.xgit3.core.local.tree.TreeMaker;
import ananas.xgit3.core.repo.BranchManager;
import ananas.xgit3.core.repo.RepoInfo;

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
		return this.__getBank();
	}

	private LocalObjectBank __getBank() {
		LocalObjectBank bank = this._bank;
		if (bank == null) {
			File path = new File(this._path, "objects");
			HashAlgorithmProvider hash = new DefaultHashAlgorithmProvider(
					"SHA-1");
			LocalObjectPathGenerator pathGen = new DefaultLocalObjectPathGenerator(
					"xx/xxxx");
			bank = new DefaultLocalBank(path, null, hash, pathGen);
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

	@Override
	public LocalTree getTree(HashID id) {
		return new LocalGitTree(this.getBank(), id);
	}

	@Override
	public LocalCommit getCommit(HashID id) {
		return new LocalGitCommit(this.getBank(), id);
	}

	@Override
	public LocalBlob getBlob(HashID id) {
		return new LocalGitBlob(this.getBank(), id);
	}

	@Override
	public LocalTag getTag(HashID id) {
		return new LocalGitTag(this.getBank(), id);
	}

	@Override
	public RepoInfo getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BranchManager getBranchManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalObjectBank getBankLF() {
		return this.__getBank();
	}

}
