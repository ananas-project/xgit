package ananas.xgitlite.impl;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.XGLException;
import ananas.xgitlite.local.IndexManager;
import ananas.xgitlite.local.LocalObjectBank;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoConfig;
import ananas.xgitlite.local.LocalRepoConfig.Core;
import ananas.xgitlite.local.MetaManager;
import ananas.xgitlite.util.IndexBuilder;
import ananas.xgitlite.util.TreeWalker;

class LocalRepoImpl implements LocalRepo {

	private final File _repo_dir;
	private final File _work_dir;
	private LocalRepoConfig _config;
	private LocalObjectBank _obj_bank;

	public LocalRepoImpl(File path) {
		this._repo_dir = path;
		this._work_dir = path.getParentFile();
		this._config = null; // new LocalRepoConfigImpl();
	}

	@Override
	public File getRepoDirectory() {
		return this._repo_dir;
	}

	@Override
	public File getWorkingDirectory() {
		return this._work_dir;
	}

	@Override
	public void init(boolean bare) throws XGLException, IOException {

		File dir;
		if (bare) {
			dir = this._repo_dir;
		} else {
			dir = this._work_dir;
		}
		if (dir.exists()) {
			throw new XGLException("the directory is existed - " + dir);
		}

		this._repo_dir.mkdirs();

		String[] list = { Name.branches, Name.hooks, Name.info, Name.logs,
				Name.objects, Name.refs, Name.xgit_dir };
		for (String s : list) {
			dir = new File(_repo_dir, s);
			dir.mkdirs();
		}

		File file = new File(_repo_dir, Name.xgit_conf);
		LocalRepoConfig conf = new LocalRepoConfigImpl();
		Core cc = conf.getCore();
		cc.set(LocalRepoConfig.Core.bare, Boolean.toString(bare));
		cc.set(LocalRepoConfig.Core.xgit, Boolean.toString(true));
		cc.set(LocalRepoConfig.Core.filemode, Boolean.toString(true));
		cc.set(LocalRepoConfig.Core.repositoryformatversion, "0");
		conf.save(file);
		this._config = conf;

	}

	@Override
	public LocalRepoConfig getConfig() {
		return this._config;
	}

	@Override
	public void check() throws XGLException, IOException {

		String[] list = { Name.branches, Name.objects, Name.refs, Name.xgit_dir };
		for (String s : list) {
			File dir = new File(_repo_dir, s);
			if (dir.exists() && dir.isDirectory()) {
				// ok, do nothing
			} else {
				throw new XGLException("the required directory not existed : "
						+ dir);
			}
		}

		LocalRepoConfig conf = new LocalRepoConfigImpl();
		conf.load(new File(_repo_dir, Name.xgit_conf));
		this._config = conf;

	}

	@Override
	public void add(File path) throws IOException, XGLException {

		LocalRepo repo = this;
		IndexBuilder builder = new IndexBuilder(repo);

		builder.begin(path);

		TreeWalker tw = new TreeWalker();
		tw.go(path, builder);

		builder.end();

	}

	@Override
	public void commit(File path) {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalObjectBank getObjectBank() {
		LocalObjectBank bank = this._obj_bank;
		if (bank == null) {
			File dir = new File(_repo_dir, LocalRepo.Name.objects);
			bank = new LocalObjectBankImpl(dir);
			this._obj_bank = bank;
		}
		return bank;
	}

	@Override
	public IndexManager getIndexManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MetaManager getMetaManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
