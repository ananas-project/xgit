package ananas.xgit3.core.remote;

import ananas.xgit3.core.util.ModuleT;

class ThisModule extends ModuleT<RemoteModule> {

	private final static ThisModule _inst;

	static {
		_inst = new ThisModule();
	}

	public static RemoteModule getModule() {
		return _inst.getModuleT();
	}
}
