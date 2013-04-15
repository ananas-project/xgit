package ananas.lib.xgit.impl;

import ananas.lib.dtml.DTDirectory;
import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.BaseRepository;
import ananas.lib.xgit.NodeMonitor;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryFactory;
import ananas.lib.xgit.XGitEnvironment;
import ananas.lib.xgit.XGitException;

public class RespositoryFactoryImpl implements RepositoryFactory {

	@Override
	public Repository openRepository(VFile file, boolean bare,
			XGitEnvironment envi) throws XGitException {

		DTWorkspace works = this._getWorkspace(file);
		if (!works.check()) {
			// TODO ...
			throw new XGitException("check failed.");
		}
		Repository repo = this._newRepository(works, bare, envi);
		return repo;
	}

	@Override
	public Repository createNewRepository(VFile file, boolean bare,
			XGitEnvironment envi) throws XGitException {

		DTWorkspace works = this._getWorkspace(file);
		if (!works.init()) {
			// TODO ...
			throw new XGitException("init failed.");
		}
		Repository repo = this._newRepository(works, bare, envi);
		return repo;
	}

	private DTWorkspace _getWorkspace(VFile file) {

		DTWorkspaceFactory factory = (new XGitWorkspaceFactoryLoader())
				.getFactory();

		DTWorkspace works = factory.createWorkspace(file);
		return works;
	}

	private Repository _newRepository(DTWorkspace works, boolean bare,
			XGitEnvironment envi) {

		BaseRepository baseRepo = new BaseRepositoryImpl(envi, bare);
		Repository repo = new RepositoryImpl(baseRepo);
		MyDCProvider prov = new MyDCProvider(baseRepo, works);
		// TODO add monitors at here
		if (!bare) {
			DirMonitorContext dc = prov.newDC(R.id.working_dir);
			NodeMonitor monitor = new TheWorkingDirectoryImpl(dc);
			prov.reg(monitor);
		}
		// and
		{
			DirMonitorContext dc = prov.newDC(R.id.objects);
			NodeMonitor monitor = new TheObjectsManagerImpl(dc);
			prov.reg(monitor);
		}
		// and
		{
			DirMonitorContext dc = prov.newDC(R.id.the_main);
			NodeMonitor monitor = new TheRepoDirectoryImpl(dc);
			prov.reg(monitor);
		}
		return repo;
	}

	class MyDCProvider {

		private final BaseRepository mBaseRepo;
		private final DTWorkspace mWorks;

		public MyDCProvider(BaseRepository baseRepo, DTWorkspace works) {
			this.mBaseRepo = baseRepo;
			this.mWorks = works;
		}

		public void reg(NodeMonitor moni) {
			this.mBaseRepo.registerMonitor(moni);
		}

		public DirMonitorContext newDC(String id) {
			DTDirectory dtDir = this.mWorks.findDirectoryById(id);
			return new MyDirMonitorContext(this.mBaseRepo, dtDir);
		}
	}

	class MyDirMonitorContext implements DirMonitorContext {

		private final DTDirectory mDtDir;
		private final BaseRepository mRepo;

		public MyDirMonitorContext(BaseRepository repo, DTDirectory dtDir) {
			this.mDtDir = dtDir;
			this.mRepo = repo;
		}

		@Override
		public BaseRepository getRepo() {
			return this.mRepo;
		}

		@Override
		public VFile getFile() {
			return this.mDtDir.getFile();
		}
	}
}
