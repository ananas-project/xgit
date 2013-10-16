package ananas.lib.xgit.repo.local;

import ananas.lib.xgit.repo.ObjectBank;
import ananas.lib.xgit.repo.ObjectId;

public interface LocalObjectBank extends ObjectBank {

	LocalObject getObject(ObjectId id);
}
