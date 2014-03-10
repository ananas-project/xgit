package impl.ananas.xgit3.core.local;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalTag;

public class LocalGitTag extends AbstractLocalGitObject implements LocalTag {

	public LocalGitTag(LocalObjectBank bank, HashID id) {
		super(bank.get(id));
	}

}
