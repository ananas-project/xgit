package ananas.xgit3.core;

import ananas.xgit3.core.util.ModuleT;

class ThisModule extends ModuleT<CoreModule> {

	private final static ThisModule _inst;

	static {
		_inst = new ThisModule();
	}

	public static CoreModule getModule() {
		return _inst.getModuleT();
	}
}
