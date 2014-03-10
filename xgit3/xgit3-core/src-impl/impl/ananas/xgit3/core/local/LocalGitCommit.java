package impl.ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalCommit;
import ananas.xgit3.core.local.LocalObjectBank;

public class LocalGitCommit extends AbstractLocalGitObject implements
		LocalCommit {

	public LocalGitCommit(LocalObjectBank bank, HashID id) {
		super(bank.get(id));
	}

}
