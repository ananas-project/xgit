package ananas.lib.xgit.impl.extension;

import java.io.File;

import ananas.lib.xgit.AbstractMonitor;
import ananas.lib.xgit.extension.XGitExtensionsDirectory;
import ananas.lib.xgit.extension.XGitIndexFile;
import ananas.lib.xgit.extension.XGitIndexManager;
import ananas.lib.xgit.extension.XGitObjectsMetaDataManager;
import ananas.lib.xgit.impl.AbstractDirectoryMonitor;

public class XGitExManagerImpl extends AbstractDirectoryMonitor implements
		XGitExtensionsDirectory {

	private XGitObjectsMetaDataManager mMetaDataManager;
	private XGitIndexManager mIndexManager;
	private XGitIndexFile mIndexFile;

	public XGitExManagerImpl(File dir) {
		super(dir);
		this.mIndexFile = new IndexFileImpl(new File(dir, "index"));
		this.mIndexManager = new IndexManagerImpl(new File(dir, "indexMap"));
		this.mMetaDataManager = new MetaDataManagerImpl(new File(dir, "meta"));
	}

	@Override
	public XGitIndexFile getIndexFile() {
		return this.mIndexFile;
	}

	@Override
	public XGitIndexManager getIndexManager() {
		return this.mIndexManager;
	}

	@Override
	public XGitObjectsMetaDataManager getObjectsMetaDataManager() {
		return this.mMetaDataManager;
	}

	@Override
	public boolean init() {
		if (!super.init()) {
			return false;
		}
		AbstractMonitor[] list = this._getSubMonitorList();
		for (AbstractMonitor mo : list) {
			if (!mo.init()) {
				return false;
			}
		}
		return true;
	}

	private AbstractMonitor[] _getSubMonitorList() {
		AbstractMonitor array[] = { this.mIndexFile, this.mIndexManager,
				this.mMetaDataManager };
		return array;
	}

	@Override
	public boolean check() {
		if (!super.check()) {
			return false;
		}
		AbstractMonitor[] list = this._getSubMonitorList();
		for (AbstractMonitor mo : list) {
			if (!mo.check()) {
				return false;
			}
		}
		return true;
	}

}
