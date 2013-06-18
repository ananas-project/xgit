package ananas.lib.xgit.task;

import ananas.lib.xgit.XGitRepo;

public interface XGitTaskRegistrar {

	void register(Class<?> api, Class<?> taskRunnableFactoryClass);

	XGitTaskRunnableFactory getFactory(Class<?> api);

	XGitTaskRunnable createTask(XGitRepo repo, Class<?> api);
}
