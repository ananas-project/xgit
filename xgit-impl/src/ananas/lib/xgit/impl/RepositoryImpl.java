package ananas.lib.xgit.impl;

import ananas.lib.xgit.BaseRepository;
import ananas.lib.xgit.BranchesManager;
import ananas.lib.xgit.ConfigFile;
import ananas.lib.xgit.HeadFile;
import ananas.lib.xgit.HooksManager;
import ananas.lib.xgit.LogsManager;
import ananas.lib.xgit.NodeMonitor;
import ananas.lib.xgit.ObjectsManager;
import ananas.lib.xgit.RefsManager;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.RepositoryDirectory;
import ananas.lib.xgit.WorkingDirectory;
import ananas.lib.xgit.XGitEnvironment;
import ananas.lib.xgit.extension.XGitExtensionsDirectory;

final class RepositoryImpl implements Repository {

	// private final DTWorkspace mWorks;
	private final BaseRepository mReg;
	//
	private ObjectsManager mObjectsMan;
	private WorkingDirectory mWorkingDir;
	private LogsManager mLogsMan;
	private HeadFile mHeadFile;
	private ConfigFile mConfigFile;
	private RefsManager mRefsMan;
	private RepositoryDirectory mRepoDir;
	private XGitExtensionsDirectory mExDir;
	private BranchesManager mBranshesMan;
	private HooksManager mHooksMan;

	public RepositoryImpl(BaseRepository reg) {
		this.mReg = reg;
	}

	@Override
	public NodeMonitor getMonitor(Class<?> apiClass) {
		return this.mReg.getMonitor(apiClass);
	}

	@Override
	public XGitEnvironment getEnvironment() {
		return this.mReg.getEnvironment();
	}

	@Override
	public WorkingDirectory getWorkingDirectory() {
		WorkingDirectory ret = this.mWorkingDir;
		if (ret == null) {
			ret = (WorkingDirectory) this.getMonitor(WorkingDirectory.class);
			this.mWorkingDir = ret;
		}
		return ret;
	}

	@Override
	public RepositoryDirectory getRepoDirectory() {
		RepositoryDirectory ret = this.mRepoDir;
		if (ret == null) {
			ret = (RepositoryDirectory) this
					.getMonitor(RepositoryDirectory.class);
			this.mRepoDir = ret;
		}
		return ret;
	}

	@Override
	public XGitExtensionsDirectory getXGitExtensionsManager() {
		XGitExtensionsDirectory ret = this.mExDir;
		if (ret == null) {
			ret = (XGitExtensionsDirectory) this
					.getMonitor(XGitExtensionsDirectory.class);
			this.mExDir = ret;
		}
		return ret;
	}

	@Override
	public BranchesManager getBranchesManager() {
		BranchesManager ret = this.mBranshesMan;
		if (ret == null) {
			ret = (BranchesManager) this.getMonitor(BranchesManager.class);
			this.mBranshesMan = ret;
		}
		return ret;
	}

	@Override
	public ObjectsManager getObjectsManager() {
		ObjectsManager ret = this.mObjectsMan;
		if (ret == null) {
			ret = (ObjectsManager) this.getMonitor(ObjectsManager.class);
			this.mObjectsMan = ret;
		}
		return ret;
	}

	@Override
	public LogsManager getLogsManager() {
		LogsManager ret = this.mLogsMan;
		if (ret == null) {
			ret = (LogsManager) this.getMonitor(LogsManager.class);
			this.mLogsMan = ret;
		}
		return ret;
	}

	@Override
	public HooksManager getHooksManager() {
		HooksManager ret = this.mHooksMan;
		if (ret == null) {
			ret = (HooksManager) this.getMonitor(HooksManager.class);
			this.mHooksMan = ret;
		}
		return ret;
	}

	@Override
	public RefsManager getRefsManager() {
		RefsManager ret = this.mRefsMan;
		if (ret == null) {
			ret = (RefsManager) this.getMonitor(RefsManager.class);
			this.mRefsMan = ret;
		}
		return ret;
	}

	@Override
	public ConfigFile getFileConfig() {
		ConfigFile ret = this.mConfigFile;
		if (ret == null) {
			ret = (ConfigFile) this.getMonitor(ConfigFile.class);
			this.mConfigFile = ret;
		}
		return ret;
	}

	@Override
	public HeadFile getFileHEAD() {
		HeadFile ret = this.mHeadFile;
		if (ret == null) {
			ret = (HeadFile) this.getMonitor(HeadFile.class);
			this.mHeadFile = ret;
		}
		return ret;
	}

	@Override
	public boolean isBare() {
		return this.mReg.isBare();
	}

	@Override
	public void registerMonitor(NodeMonitor monitor) {
		this.mReg.registerMonitor(monitor);
	}

}
