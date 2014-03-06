package impl.ananas.xgit3.core.local;

import java.io.File;

import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalObjectBankFactory;

public class LocalObjectBankFactoryImpl implements LocalObjectBankFactory {

	private String _alg = "SHA-256";
	private String _opp = "xx/xxxxxxxxxxx";

	@Override
	public LocalObjectBank createBank(File path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHashAlgorithm(String algorithm) {
		if (algorithm != null)
			this._alg = algorithm;
	}

	@Override
	public String getHashAlgorithm() {
		return this._alg;
	}

	@Override
	public void setObjectPathPattern(String opp) {
		if (opp != null)
			this._opp = opp;
	}

	@Override
	public String getObjectPathPattern() {
		return this._opp;
	}

}
