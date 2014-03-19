package ananas.xgit3.core.bank;

import ananas.xgit3.core.HashID;

public interface ObjectBank {

	ZippedObject get(HashID id);
}
