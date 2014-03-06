package ananas.xgit3.core;

import java.security.MessageDigest;

public interface HashAlgorithmProvider {

	MessageDigest createMessageDigest();

	String getName();

}
