package impl.ananas.xgit3.core;

import ananas.xgit3.core.CoreModule;
import ananas.xgit3.core.HashID;

public class ThisModule implements CoreModule {

	@Override
	public HashID newHashID(String s) {
		return DefaultHashID.create(s);
	}

	@Override
	public HashID newHashID(byte[] b) {
		return DefaultHashID.create(b);
	}

}
