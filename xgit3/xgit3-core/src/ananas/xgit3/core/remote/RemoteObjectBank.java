package ananas.xgit3.core.remote;

import ananas.xgit3.core.HashID;

public interface RemoteObjectBank {

	RemoteObject getObject(HashID id);

}
