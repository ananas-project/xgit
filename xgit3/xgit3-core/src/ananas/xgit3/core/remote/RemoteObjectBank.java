package ananas.xgit3.core.remote;

import java.util.Enumeration;

import ananas.xgit3.core.HashID;

public interface RemoteObjectBank {

	RemoteObject get(HashID id);

	Enumeration<RemoteObject> get(Enumeration<HashID> ids);

}
