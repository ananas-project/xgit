package ananas.lib.xgit.util;

import ananas.fileworks.Component;
import ananas.fileworks.Context;
import ananas.fileworks.task.Task;
import ananas.lib.xgit.boot.XGitBooterFactory;

public class DefaultXGitBooterFactory implements Component, XGitBooterFactory {

	@Override
	public Task createTask(Context context) {
		return new DefaultXGitBooter(context);
	}

}
