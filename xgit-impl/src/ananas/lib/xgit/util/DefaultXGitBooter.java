package ananas.lib.xgit.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Context;
import ananas.fileworks.task.Task;
import ananas.fileworks.task.TaskRunner;
import ananas.fileworks.util.DefaultTaskRunner;
import ananas.lib.xgit.task.DefaultXGitTaskFactory;
import ananas.lib.xgit.task.XGitTaskFactory;

public class DefaultXGitBooter implements Task {

	private final Context mContext;
	private String mStatus = Task.status_init;
	private TaskRunner mRunner;

	// private boolean mDoCancel;
	// private boolean mDoStart;

	public DefaultXGitBooter(Context context) {
		this.mContext = context;
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
		// this.mDoCancel = true;
	}

	@Override
	public void start() {
		// this.mDoStart = true;
		this.getRunner().addTask(this);
	}

	@Override
	public Runnable getRunnable() {
		return this.mRunnable;
	}

	private final Runnable mRunnable = new Runnable() {

		@Override
		public void run() {

			System.out.println(this + ".run()");

			DefaultXGitBooter.this.doBoot();
		}
	};

	protected void doBoot() {

		ComponentRegistrar cr = this.mContext.getEnvironment()
				.getComponentRegistrar();
		ComponentManager single = this.mContext.getEnvironment()
				.getSingletonManager();

		// register class
		{
			cr.register(XGitTaskFactory.class, DefaultXGitTaskFactory.class);
			cr.register(TaskRunner.class, DefaultTaskRunner.class);
		}
		// declare singleton
		{
			single.declare(XGitTaskFactory.class, null, true);
			single.declare(TaskRunner.class, null, true);
		}

	}

}
