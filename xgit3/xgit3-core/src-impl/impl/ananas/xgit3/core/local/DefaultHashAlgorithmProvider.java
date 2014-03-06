package impl.ananas.xgit3.core.local;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ananas.xgit3.core.HashAlgorithmProvider;

public class DefaultHashAlgorithmProvider implements HashAlgorithmProvider {

	private final String _name;

	public DefaultHashAlgorithmProvider(String alg) {
		this._name = alg;
	}

	@Override
	public MessageDigest createMessageDigest() {
		try {
			return MessageDigest.getInstance(_name);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return this._name;
	}

}
