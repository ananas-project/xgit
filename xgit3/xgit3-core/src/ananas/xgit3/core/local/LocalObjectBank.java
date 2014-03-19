package ananas.xgit3.core.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.bank.ObjectBank;

public interface LocalObjectBank extends DirectoryNode, ObjectBank {

	LocalObject get(HashID id);

	LocalObject add(String type, long length, InputStream in)
			throws IOException;

	LocalObject add(String type, File file) throws IOException;

	File getTempDirectory();

	File newTempFile(String ref_name);

	LocalObjectPathGenerator getPathGenerator();

	HashAlgorithmProvider getHashAlgorithmProvider();

	class Factory {

		public static LocalObjectBankFactory getFactory() {
			return ThisModule.getModule().newBankFactory();
		}

	}

}
