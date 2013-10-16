package ananas.lib.xgit.repo.remote;

import ananas.lib.xgit.repo.XGitObject;

public interface RemoteObject extends XGitObject {

	RemoteObjectBank getBank();

}
