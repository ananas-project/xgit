package ananas.xgit3.core.repo;

import ananas.xgit3.core.util.ModuleT;

final class ThisModule extends ModuleT<RepoModule> {

	private final static ThisModule _inst;

	static {
		_inst = new ThisModule();
	}

	public static RepoModule getModule() {
		return _inst.getModuleT();
	}
}
