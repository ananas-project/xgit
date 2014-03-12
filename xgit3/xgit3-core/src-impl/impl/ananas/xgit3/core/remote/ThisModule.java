package impl.ananas.xgit3.core.remote;

import ananas.xgit3.core.remote.RemoteContext;
import ananas.xgit3.core.remote.RemoteModule;

public class ThisModule implements RemoteModule {

	@Override
	public RemoteContext newDefaultContext() {
		return new BaseRemoteContext();
	}

}
