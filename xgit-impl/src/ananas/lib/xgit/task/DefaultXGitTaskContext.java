package ananas.lib.xgit.task;

import ananas.fileworks.Context;
import ananas.fileworks.task.TaskRunner;
import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.XGitRepo;

public class DefaultXGitTaskContext implements XGitTaskContext {

	private final Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			DefaultXGitTaskContext.this.doRun();
		}
	};
	private final Context mContext;
	private XGitTaskRunnable mTaskRunnable;
	private String mStatus;
	private TaskRunner mRunner;
	private final XGitRepo mRepo;
	private boolean mIsStart;
	private boolean mIsCancel;

	public DefaultXGitTaskContext(XGitRepo repo) {
		this.mRepo = repo;
		this.mContext = repo.getContext();
	}

	private void doRun() {

		if (!this.mIsStart) {
			return;
		}

		try {
			this.mTaskRunnable.run(this);
		} catch (XGitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getStatus() {
		return this.mStatus;
	}

	@Override
	public TaskRunner getRunner() {
		return this.mRunner;
	}

	@Override
	public void setRunner(TaskRunner runner) {
		this.mRunner = runner;
	}

	@Override
	public Context getContext() {
		return this.mContext;
	}

	@Override
	public void cancel() {
		this.mIsCancel = true;
	}

	@Override
	public void start() {
		this.mIsStart = true;
		this.getRunner().addTask(this);
	}

	@Override
	public Runnable getRunnable() {
		return this.mRunnable;
	}

	@Override
	public XGitRepo getRepo() {
		return this.mRepo;
	}

	@Override
	public XGitTaskRunnable getTaskRunnable() {
		return this.mTaskRunnable;
	}

	@Override
	public void setTaskRunnable(XGitTaskRunnable task) {
		this.mTaskRunnable = task;
	}

	@Override
	public boolean isCancel() {
		return this.mIsCancel;
	}

}
