package impl.ananas.xgit3.core.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit3.core.HashAlgorithmProvider;
import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalObjectPathGenerator;

public class DefaultBank implements LocalObjectBank {

	private final File _bank_dir;
	private final File _temp_dir;
	private final HashAlgorithmProvider _hash_provider;
	private final LocalObjectPathGenerator _path_gen;
	private int _temp_index;

	/**
	 * @param path
	 *            the base path of this bank.
	 * @param tmpDir
	 *            , the temp directory of this bank, if null, set to default
	 *            value '{path}/temp'.
	 * 
	 * */

	public DefaultBank(File path, File tmpDir,
			HashAlgorithmProvider hash_provider,
			LocalObjectPathGenerator path_gen) {

		if (tmpDir == null) {
			tmpDir = new File(path, "temp");
		}
		if (hash_provider == null) {
			hash_provider = new DefaultHashAlgorithmProvider("SHA-256");
		}
		if (path_gen == null) {
			path_gen = new DefaultLocalObjectPathGenerator("xx/xxxx");
		}

		this._hash_provider = hash_provider;
		this._path_gen = path_gen;
		this._bank_dir = path;
		this._temp_dir = tmpDir;
	}

	@Override
	public File getPath() {
		return this._bank_dir;
	}

	@Override
	public LocalObject get(HashID id) {
		File file = this._path_gen.gen(id, _bank_dir);
		return new DefaultLocalObject(this, id, file);
	}

	@Override
	public LocalObject add(String type, long length, InputStream in)
			throws IOException {

		LocalObjectBank bank = this;
		byte[] buf = new byte[1024];
		SuperLocalObjectBuilder builder = SuperLocalObjectBuilder.Factory
				.newPlainBuilder(bank);
		OutputStream out = builder.openOutputStream(type, length);
		for (;;) {
			int cb = in.read(buf);
			if (cb < 0)
				break;
			out.write(buf, 0, cb);
		}
		out.close();
		return builder.getResult();
	}

	@Override
	public LocalObject add(String type, File file) throws IOException {
		long len = file.length();
		InputStream in = new FileInputStream(file);
		LocalObject obj = this.add(type, len, in);
		in.close();
		return obj;
	}

	@Override
	public File getTempDirectory() {
		return this._temp_dir;
	}

	@Override
	public File newTempFile(String ref_name) {

		long now = System.currentTimeMillis();
		long index = (++this._temp_index);
		long thisid = this.hashCode() & 0x7fffffff;
		long ref = (ref_name == null) ? 1 : (ref_name.hashCode() & 0x7fffffff);
		char sp = '_';

		StringBuilder sb = new StringBuilder();
		sb.append(sp);
		sb.append(now);
		sb.append(sp);
		sb.append(thisid);
		sb.append(sp);
		sb.append(index);
		sb.append(sp);
		sb.append(ref);
		sb.append(".temp");
		return new File(this._temp_dir, sb.toString());
	}

	@Override
	public LocalObjectPathGenerator getPathGenerator() {
		return this._path_gen;
	}

	@Override
	public HashAlgorithmProvider getHashAlgorithmProvider() {
		return this._hash_provider;
	}

}
