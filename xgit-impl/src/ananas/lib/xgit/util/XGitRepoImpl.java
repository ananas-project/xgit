package ananas.lib.xgit.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.Context;
import ananas.fileworks.util.ComponentCache;
import ananas.fileworks.util.Refer;
import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitWorkspace;
import ananas.lib.xgit.task.XGitTaskFactory;

final class XGitRepoImpl implements XGitRepo {

	private final Context mContext;
	private XGitWorkspace mWorkspace;
	//
	private final Refer<XGitTaskFactory> m_taskFactory;

	public XGitRepoImpl(Context context) {
		this.mContext = context;

		ComponentManager single = context.getEnvironment()
				.getSingletonManager();

		this.m_taskFactory = new ComponentCache<XGitTaskFactory>(single,
				XGitTaskFactory.class, null);

	}

	@Override
	public Context getContext() {
		return this.mContext;
	}

	@Override
	public XGitWorkspace getWorkspace() {
		XGitWorkspace works = this.mWorkspace;
		if (works == null) {
			VFile path = this.mContext.getMainPath();
			works = new XGitWorkspaceImpl(path);
			this.mWorkspace = works;
		}
		return works;
	}

	@Override
	public XGitTaskFactory getTaskFactory() {
		return this.m_taskFactory.get();
	}

}
