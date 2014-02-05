package ananas.xgitlite.xmail;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.LocalRepo;

public class XMailFactoryWrapper implements XMailFactory {

	private final XMailFactory target;

	public XMailFactoryWrapper(XMailFactory target) {
		this.target = target;
	}

	@Override
	public XCommitBuilder createBuilder(LocalRepo repo) {
		return target.createBuilder(repo);
	}

	@Override
	public XCommit getCommit(LocalRepo repo, ObjectId id) {
		return target.getCommit(repo, id);
	}

}
