package impl.ananas.xgit3.core.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;

public class DefaultBank implements LocalObjectBank {

	private final File _bank_dir;
	private final File _temp_dir;
	private final int _path_depth;
	private int _temp_index;

	/**
	 * @param path
	 *            the base path of this bank.
	 * @param tmpDir
	 *            , the temp directory of this bank, if null, set to default
	 *            value '{path}/temp'.
	 * @param pathDepth
	 *            the depth of path for object in this bank, if 0, set to
	 *            default '2'.
	 * */

	public DefaultBank(File path, File tmpDir, int pathDepth) {
		this._bank_dir = path;
		if (tmpDir == null) {
			tmpDir = new File(path, "temp");
		}
		if (pathDepth < 2)
			pathDepth = 2;
		if (pathDepth > 10)
			pathDepth = 10;
		this._path_depth = pathDepth;
		this._temp_dir = tmpDir;
	}

	@Override
	public File getPath() {
		return this._bank_dir;
	}

	@Override
	public LocalObject get(HashID id) {
		String off = this.getOffsetPath(id);
		File file = new File(this._bank_dir, off);
		return new DefaultLocalObject(this, id, file);
	}

	private String getOffsetPath(HashID id) {
		final char sep = File.separatorChar;
		final StringBuilder sb = new StringBuilder();
		final String s = id.toString();
		int i = 0;
		for (; i < this._path_depth; i += 2) {
			sb.append(s.substring(i, i + 2));
			sb.append(sep);
		}
		sb.append(s.substring(i));
		return sb.toString();
	}

	@Override
	public LocalObject add(String type, long length, InputStream in)
			throws IOException {

		File tmp = this.newTempFile(type + "  " + length);
		LocalObjectBuilder builder = new LocalObjectBuilder(type, length, tmp);
		builder.begin();
		builder.loadData(in);
		builder.end();
		Throwable err = builder.getError();
		if (err == null) {
			HashID id = builder.getId();
			LocalObject obj = this.get(id);
			if (obj.exists())
				builder.drop();
			else
				builder.moveTo(obj.getPath());
			return obj;
		} else {
			builder.drop();
			throw new RuntimeException(err);
		}
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

}
