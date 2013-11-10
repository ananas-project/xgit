package test.ananas.lib.xgit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class TestFileScan {

	public static void main(String[] arg) {

		File file = new File("/");
		Handler h = new MyHandler();
		TestFileScan sc = new TestFileScan();
		sc.scan(file, h);

	}

	private void scan(File file, Handler h) {

		long t1 = System.currentTimeMillis();
		this.__scan(file, h, 0);
		long t2 = System.currentTimeMillis();
		System.out.println("cost " + ((t2 - t1) / 1000) + " sec");
		h.onAllEnd();

	}

	interface Const {

		int depth_limit = 32;
	}

	private void __scan(File file, Handler h, int deep) {

		if (deep > Const.depth_limit) {
			System.err.println("warning:too deep : " + file);
			h.onTooDeep(file);
			return;
		}

		try {

			String p1, p2;
			p1 = file.getAbsolutePath();
			p2 = file.getCanonicalPath();
			if (!p1.equals(p2)) {
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (file.isDirectory()) {
			h.onDirBegin(file, deep);
			File[] chs = file.listFiles();
			if (chs != null) {
				for (File ch : chs) {
					this.__scan(ch, h, deep + 1);
				}
			}
			h.onDirEnd(file);
		} else {
			h.onFile(file);
		}

	}

	interface Handler {

		void onDirBegin(File file, int deep);

		void onTooDeep(File file);

		void onFile(File file);

		void onDirEnd(File file);

		void onAllEnd();

	}

	static class MyHandler implements Handler {

		private int _cnt_file;
		private int _cnt_dir;
		private int _cnt_too_deep;
		private int _depth_max;
		private File _depth_max_file;

		@Override
		public void onDirBegin(File file, int deep) {

			// System.out.println("dir:  " + file);

			this._cnt_dir++;

			if (this._depth_max < deep) {
				this._depth_max = deep;
				this._depth_max_file = file;
			}

			this.__hash_path(file);

		}

		@Override
		public void onFile(File file) {

			file.length();
			file.lastModified();
			file.getName();

			this._cnt_file++;

			this.__hash_path(file);

		}

		final Hasher _hasher = new Hasher();

		final Map<String, String> _map = new HashMap<String, String>();

		private void __hash_path(File file) {

			boolean skip = false;

			if (skip)
				return;

			try {
				String s = file.getCanonicalPath();
				String ha = _hasher.hash(s);
				String rlt = _map.get(ha);
				if (rlt == null)
					_map.put(ha, s);
				else {
					System.out.println(ha + "  bang  " + s);
					System.out.println(ha + "  bang  " + rlt);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onDirEnd(File file) {

			if (this._cnt_dir % 1000 == 0)
				System.out.println("cnt_file:" + this._cnt_file + "  cnt_dir:"
						+ this._cnt_dir + "   path:" + file);

		}

		@Override
		public void onTooDeep(File file) {

			this._cnt_too_deep++;

		}

		@Override
		public void onAllEnd() {

			System.out.println("count dir      : " + this._cnt_dir);
			System.out.println("count file     : " + this._cnt_file);
			System.out.println("count too deep : " + this._cnt_too_deep);
			System.out.println("depth max      : " + this._depth_max);
			System.out.println("depth max file : " + this._depth_max_file);

		}
	}

	static class Hasher {

		final MessageDigest md;
		final StringBuilder sb = new StringBuilder();
		final char[] chs = "0123456789abcdef".toCharArray();

		public Hasher() {
			MessageDigest sha1 = null;
			try {
				sha1 = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			md = sha1;
		}

		public String hash(String s) throws UnsupportedEncodingException {
			sb.setLength(0);
			md.reset();
			byte[] ba = s.getBytes("UTF-8");
			ba = md.digest(ba);
			for (byte b : ba) {
				sb.append(chs[(b >> 4) & 0x0f]);
				sb.append(chs[(b) & 0x0f]);
			}
			return sb.toString();
		}
	}
}
