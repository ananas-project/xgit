package impl.ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalTree;

public class LocalGitTree extends AbstractLocalGitObject implements
		LocalTree {

	public LocalGitTree(LocalObjectBank bank, HashID id) {
		super(bank.get(id));
	}

}
