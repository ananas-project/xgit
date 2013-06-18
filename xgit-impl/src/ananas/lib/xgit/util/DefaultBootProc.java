package ananas.lib.xgit.util;

import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.task.XGitTaskContext;
import ananas.lib.xgit.task.XGitTaskRunnable;
import ananas.lib.xgit.task.XGitTaskRunnableFactory;
import ananas.lib.xgit.task.ext.RepoBoot;

public class DefaultBootProc implements XGitTaskRunnableFactory {

	@Override
	public XGitTaskRunnable createTaskRunnable() {
		return new MyRunnable();
	}

	private static class MyRunnable extends AbstractBootProc implements
			RepoBoot, XGitTaskRunnable {

		@Override
		public void run(XGitTaskContext context) throws XGitException {

			super.run(context);

		}
	}

}
