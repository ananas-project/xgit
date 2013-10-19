package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileOutputStream;

public class LargeLocalObjectBuilder extends AbstractLocalObjectBuilder {

	private final VFile _temp_file;

	public LargeLocalObjectBuilder(VFile dir, String type, long length) {
		super(length);

		String s = "[" + type + "|" + length + "|" + this + "|"
				+ System.currentTimeMillis() + "]";

		_temp_file = dir.getVFS().newFile(dir, "tmp/" + (new Hash()).hash(s));

		System.out.println("create temp file : " + _temp_file);

	}

	@Override
	public void delete() {
		_temp_file.delete();
	}

	@Override
	public void moveTo(VFile path) throws IOException {
		VFile pa = path.getParentFile();
		if (!pa.exists()) {
			pa.mkdirs();
		}
		_temp_file.renameTo(path);
	}

	@Override
	protected OutputStream openDataOutput() {

		VFile file = _temp_file;
		VFile dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream fos = new VFileOutputStream(file);
		OutputStream dos = new DeflaterOutputStream(fos);
		return dos;
	}

	static class Hash {

		static final char[] chs = "0123456789ABCDEF".toCharArray();

		public String hash(String s) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] res = md.digest(s.getBytes());
				StringBuilder sb = new StringBuilder();
				for (byte b : res) {
					sb.append(chs[0x0f & b]);
					sb.append(chs[0x0f & (b >> 4)]);
				}
				return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return (s.hashCode() + "");
			}
		}

	}

}
