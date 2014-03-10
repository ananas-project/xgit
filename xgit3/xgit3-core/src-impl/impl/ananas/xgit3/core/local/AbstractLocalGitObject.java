package impl.ananas.xgit3.core.local;

import ananas.xgit3.core.local.LocalGitObject;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectWrapper;

public class AbstractLocalGitObject extends LocalObjectWrapper implements
		LocalGitObject {

	public AbstractLocalGitObject(LocalObject _target) {
		super(_target);
	}

}
