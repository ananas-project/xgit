package ananas.xgitlite.impl;

import java.io.File;
import java.io.IOException;

import ananas.xgitlite.LocalObject;
import ananas.xgitlite.LocalRepo;
import ananas.xgitlite.LocalRepoConfig;
import ananas.xgitlite.LocalRepoConfig.Core;
import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;

public class LocalRepoImpl implements LocalRepo {

	private final File _repo_dir;
	private final File _work_dir;
	private LocalRepoConfig _config;

	public LocalRepoImpl(File path) {
		this._repo_dir = path;
		this._work_dir = path.getParentFile();
		this._config = new LocalRepoConfigImpl();
	}

	@Override
	public File getRepoDirectory() {
		return this._repo_dir;
	}

	@Override
	public LocalObject getObject(ObjectId id) {
		// TODO Auto-generated method stub
		return null;
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
		LocalRepoConfig conf = this.getConfig();
		Core cc = conf.getCore();
		cc.set(LocalRepoConfig.Core.bare, Boolean.toString(bare));
		cc.set(LocalRepoConfig.Core.xgit, Boolean.toString(true));
		cc.set(LocalRepoConfig.Core.filemode, Boolean.toString(true));
		cc.set(LocalRepoConfig.Core.repositoryformatversion, "0");
		conf.save(file);

	}

	@Override
	public LocalRepoConfig getConfig() {
		return this._config;
	}

}
