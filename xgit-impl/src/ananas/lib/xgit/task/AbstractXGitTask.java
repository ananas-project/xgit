package ananas.lib.xgit.task;

public abstract class AbstractXGitTask implements XGitTaskRunnable {

	private XGitTaskContext mContext;

	@Override
	public XGitTaskContext getTaskContext() {
		return this.mContext;
	}

	@Override
	public void setTaskContext(XGitTaskContext context) {
		this.mContext = context;
	}

}
