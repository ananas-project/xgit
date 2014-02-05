package ananas.xgitlite.xmail;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;

public class XMailFactoryWrapper implements XMailFactory {

	private final XMailFactory target;

	public XMailFactoryWrapper(XMailFactory target) {
		this.target = target;
	}

	@Override
	public XCommitBuilder createBuilder(Repo repo) {
		return target.createBuilder(repo);
	}

	@Override
	public XCommit getCommit(ObjectId id) {
		return target.getCommit(id);
	}

}
