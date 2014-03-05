package ananas.xgit3.core.local;

import ananas.xgit3.core.util.ModuleT;

class ThisModule extends ModuleT<LocalModule> {

	private final static ThisModule _inst;

	static {
		_inst = new ThisModule();
	}

	public static LocalModule getModule() {
		return _inst.getModuleT();
	}
}
