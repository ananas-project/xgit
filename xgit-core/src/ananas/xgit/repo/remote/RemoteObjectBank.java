package ananas.xgit.repo.remote;

import ananas.xgit.repo.ObjectBank;
import ananas.xgit.repo.ObjectId;

public interface RemoteObjectBank extends ObjectBank {

	RemoteObject getObject(ObjectId id);

}
