package ananas.lib.xgit.util;

import java.util.Hashtable;
import java.util.Map;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentFactory;
import ananas.fileworks.Context;
import ananas.fileworks.task.TaskRunner;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.task.DefaultXGitTaskContext;
import ananas.lib.xgit.task.XGitTaskContext;
import ananas.lib.xgit.task.XGitTaskRegistrar;
import ananas.lib.xgit.task.XGitTaskRunnable;
import ananas.lib.xgit.task.XGitTaskRunnableFactory;

public class DefaultXGitTaskRegistrarFactory implements ComponentFactory {

	@Override
	public Component createComponent(Context context) {
		return new MyComp();
	}

	private static class MyComp implements Component, XGitTaskRegistrar {

		private final Map<Class<?>, Class<?>> m_map_class;
		private final Map<Class<?>, XGitTaskRunnableFactory> m_map_factory;

		public MyComp() {
			this.m_map_class = new Hashtable<Class<?>, Class<?>>();
			this.m_map_factory = new Hashtable<Class<?>, XGitTaskRunnableFactory>();
		}

		@Override
		public void register(Class<?> api, Class<?> taskRunnableFactoryClass) {
			this.m_map_class.put(api, taskRunnableFactoryClass);
		}

		@Override
		public XGitTaskRunnableFactory getFactory(Class<?> api) {
			XGitTaskRunnableFactory fact = this.m_map_factory.get(api);
			if (fact == null) {
				Class<?> cls = this.m_map_class.get(api);
				if (cls == null) {
					System.err.println("no implements class register for api "
							+ api);
				} else {
					try {
						fact = (XGitTaskRunnableFactory) cls.newInstance();
						this.m_map_factory.put(api, fact);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return fact;
		}

		@Override
		public XGitTaskRunnable createTask(XGitRepo repo, Class<?> api) {
			XGitTaskRunnableFactory fact = this.getFactory(api);
			XGitTaskRunnable runnable = fact.createTaskRunnable();
			this.setupContext(repo, runnable);
			return runnable;
		}

		private void setupContext(XGitRepo repo, XGitTaskRunnable runn) {
			TaskRunner runner = (TaskRunner) repo.getContext().getEnvironment()
					.getSingletonManager().get(TaskRunner.class, null);
			XGitTaskContext context = new DefaultXGitTaskContext(repo);
			context.setRunner(runner);
			context.setTaskRunnable(runn);
			runn.setTaskContext(context);
		}

	}
}
