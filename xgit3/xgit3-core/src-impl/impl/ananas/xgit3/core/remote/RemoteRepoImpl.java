package impl.ananas.xgit3.core.remote;

import ananas.xgit3.core.remote.JSONRequest;
import ananas.xgit3.core.remote.RemoteContext;
import ananas.xgit3.core.remote.RemoteObjectBank;
import ananas.xgit3.core.remote.RemoteRepo;
import ananas.xgit3.core.remote.RemoteRepoInfo;

final class RemoteRepoImpl implements RemoteRepo {

	private final RemoteRepoInfo _info;
	private RemoteObjectBank _bank;

	public RemoteRepoImpl(RemoteRepoInfo info) {
		this._info = info;
	}

	@Override
	public RemoteObjectBank getBank() {
		RemoteObjectBank bank = this._bank;
		if (bank == null) {
			this._bank = bank = new RemoteBankImpl(this);
		}
		return bank;
	}

	@Override
	public RemoteContext getContext() {
		return this._info.getContext();
	}

	@Override
	public RemoteRepoInfo getInfo() {
		return this._info;
	}

	@Override
	public JSONRequest createJSONRequest() {
		return new JSONRequestImpl(this);
	}

}
