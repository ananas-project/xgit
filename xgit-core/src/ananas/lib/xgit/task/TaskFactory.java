package ananas.lib.xgit.task;

import java.util.Properties;

import ananas.lib.xgit.Repository;

public interface TaskFactory {

	Task createTask(Repository repo, Properties param, TaskListener listener);
}
