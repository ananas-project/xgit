package ananas.lib.xgit.task;

public interface TaskListener {

	void onStart(Task task);

	void onException(Task task, Exception exp);

	void onEnd(Task task);

}
