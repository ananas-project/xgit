package ananas.lib.xgit.repo.remote;

import ananas.lib.xgit.repo.ObjectBank;
import ananas.lib.xgit.repo.ObjectId;

public interface RemoteObjectBank extends ObjectBank {

	RemoteObject getObject(ObjectId id);

}
