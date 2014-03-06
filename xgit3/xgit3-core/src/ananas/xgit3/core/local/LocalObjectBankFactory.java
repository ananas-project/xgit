package ananas.xgit3.core.local;

import java.io.File;

public interface LocalObjectBankFactory {

	String opp_xx_xxxx = "xx/xxxx";
	String opp_xx_xx_xxxx = "xx/xx/xxxx";
	String opp_xx_xx_xx_xxxx = "xx/xx/xx/xxxx";

	String alg_sha_1 = "SHA-1";
	String alg_sha_256 = "SHA-256";

	LocalObjectBank createBank(File path);

	void setHashAlgorithm(String algorithm);

	String getHashAlgorithm();

	void setObjectPathPattern(String opp);

	String getObjectPathPattern();

}
