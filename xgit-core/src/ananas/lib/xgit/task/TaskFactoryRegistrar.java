package ananas.lib.xgit.task;

public interface TaskFactoryRegistrar {

	TaskFactory get(String name);

	void register(String name, TaskFactory factory);

}
