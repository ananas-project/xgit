package ananas.xgit3.core.bank;

import java.util.Enumeration;

import ananas.xgit3.core.HashID;

public interface ObjectBank {

	ZippedObject get(HashID id);

	Enumeration<ZippedObject> get(Enumeration<HashID> ids);

}
