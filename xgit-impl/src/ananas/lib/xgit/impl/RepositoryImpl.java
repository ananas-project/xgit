package ananas.lib.xgit.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ananas.lib.xgit.AbstractMonitor;
import ananas.lib.xgit.BranchesManager;
import ananas.lib.xgit.ConfigFile;
import ananas.lib.xgit.HeadFile;
import ananas.lib.xgit.HooksManager;
import ananas.lib.xgit.LogsManager;
import ananas.lib.xgit.ObjectsManager;
import ananas.lib.xgit.RefsManager;
import ananas.lib.xgit.Repository;
import ananas.lib.xgit.WorkingDirectory;
import ananas.lib.xgit.extension.XGitExtensionsDirectory;
import ananas.lib.xgit.impl.extension.XGitExManagerImpl;

public class RepositoryImpl extends AbstractDirectoryMonitor implements
		Repository {

	private final WorkingDirectory mWorkingDir;
	private final XGitExtensionsDirectory mXGitExNamager;
	private final BranchesManager mBranchesManager;
	private final LogsManager mLogsManager;
	private final HooksManager mHooksManager;
	private final RefsManager mRefsManager;
	private final ConfigFile mConfigFile;
	private final HeadFile mHeadFile;
	private final ObjectsManager mObjectsManager;

	public RepositoryImpl(File dirRepo, boolean bare) {
		super(dirRepo);
		if (bare) {
			this.mWorkingDir = null;
		} else {
			File dir = dirRepo.getParentFile();
			this.mWorkingDir = new WorkingDirectoryImpl(dir);
		}

		this.mConfigFile = new ConfigFileImpl(new File(dirRepo, "config"));
		this.mHeadFile = new HeadFileImpl(new File(dirRepo, "HEAD"));

		this.mObjectsManager = new ObjectManagerImpl(new File(dirRepo,
				"objects"));
		this.mLogsManager = new LogsManagerImpl(new File(dirRepo, "logs"));
		this.mRefsManager = new RefsManagerImpl(new File(dirRepo, "refs"));
		this.mHooksManager = new HooksManagerImpl(new File(dirRepo, "hooks"));
		this.mXGitExNamager = new XGitExManagerImpl(new File(dirRepo, "xgit"));
		this.mBranchesManager = new BranchesManagerImpl(new File(dirRepo,
				"branches"));

	}

	private List<AbstractMonitor> _getMonitorList() {

		List<AbstractMonitor> list = new ArrayList<AbstractMonitor>();

		if (this.mWorkingDir != null) {
			list.add(this.mWorkingDir);
		}

		list.add(this.mBranchesManager);
		list.add(this.mConfigFile);
		list.add(this.mHeadFile);
		list.add(this.mHooksManager);
		list.add(this.mLogsManager);
		list.add(this.mObjectsManager);
		list.add(this.mRefsManager);
		list.add(this.mXGitExNamager);

		return list;
	}

	@Override
	public boolean init() {
		List<AbstractMonitor> list = this._getMonitorList();
		for (AbstractMonitor mo : list) {
			if (!mo.init()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean check() {
		List<AbstractMonitor> list = this._getMonitorList();
		for (AbstractMonitor mo : list) {
			if (!mo.check()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public WorkingDirectory getWorkingDirectory() {
		return this.mWorkingDir;
	}

	@Override
	public ObjectsManager getObjectsManager() {
		return this.mObjectsManager;
	}

	@Override
	public boolean isBare() {
		return (this.mWorkingDir == null);
	}

	@Override
	public XGitExtensionsDirectory getXGitExtensionsManager() {
		return this.mXGitExNamager;
	}

	@Override
	public BranchesManager getBranchesManager() {
		return this.mBranchesManager;
	}

	@Override
	public LogsManager getLogsManager() {
		return this.mLogsManager;
	}

	@Override
	public HooksManager getHooksManager() {
		return this.mHooksManager;
	}

	@Override
	public RefsManager getRefsManager() {
		return this.mRefsManager;
	}

	@Override
	public ConfigFile getFileConfig() {
		return this.mConfigFile;
	}

	@Override
	public HeadFile getFileHEAD() {
		return this.mHeadFile;
	}

}
