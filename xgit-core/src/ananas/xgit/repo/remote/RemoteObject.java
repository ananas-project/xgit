package ananas.xgit.repo.remote;

import ananas.xgit.repo.XGitObject;

public interface RemoteObject extends XGitObject {

	RemoteObjectBank getBank();

}
