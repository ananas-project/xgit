package test.ananas.lib.xgit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;

public class TestScanObjects {

	public static void main(String[] arg) {

		try {
			TestScanObjects test = new TestScanObjects();
			test.scan();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	private void scan() throws URISyntaxException {

		File repo = this.findRepo();
		System.out.println("scan in " + repo);

		File objs = new File(repo, "objects");
		FileFilter ff = new FileFilter() {

			@Override
			public boolean accept(File path) {
				String n0 = path.getName();
				String n1 = path.getParentFile().getName();
				String n2 = path.getParentFile().getParentFile().getName();
				if (n0.length() == 38 && n1.length() == 2
						&& n2.equals("objects")) {
					this.onObject(path);
				}
				return true;
			}

			private void onObject(File path) {
				System.out.println("    object: " + path);
				MyGitObject obj = new MyGitObject(path);
				try {
					obj.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		this.listAllChildren(objs, 5, ff);

	}

	private void listAllChildren(File path, int depthLim, FileFilter ff) {
		if (depthLim < 0) {
			throw new RuntimeException("...");
		}
		final boolean acc = ff.accept(path);
		if (path.isDirectory() && acc) {
			File[] chs = path.listFiles();
			for (File ch : chs) {
				this.listAllChildren(ch, depthLim - 1, ff);
			}
		}
	}

	private File findRepo() throws URISyntaxException {
		URL loc = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		File dir = new File(loc.toURI());
		for (; dir != null; dir = dir.getParentFile()) {
			File git = new File(dir, ".git");
			if (git.exists()) {
				if (git.isDirectory()) {
					return git;
				}
			}
		}
		return null;
	}

	class MyGitObject {

		private final File _path;
		private String _type;
		private long _length;

		public MyGitObject(File path) {
			this._path = path;
		}

		public void load() throws IOException {

			InputStream in0 = new FileInputStream(this._path);
			InputStream in2 = new java.util.zip.InflaterInputStream(in0);

			StringBuilder sb = new StringBuilder();
			for (int b = in2.read(); b > 0; b = in2.read()) {
				sb.append((char) b);
			}
			String head = sb.toString();
			int i = head.lastIndexOf(" ");
			String type = head.substring(0, i);
			String len = head.substring(i + 1);
			this._type = type;
			this._length = Long.parseLong(len);

			if (this._type.equals("blob")) {
				this.onLoadBlob(in2);
			} else if (this._type.equals("tree")) {
				this.onLoadTree(in2);
			} else if (this._type.equals("commit")) {
				this.onLoadCommit(in2);
			}

			in2.close();

		}

		private void onLoadBlob(InputStream in) {
			// TODO Auto-generated method stub

		}

		private void onLoadCommit(InputStream in) throws IOException {

			PrintStream out = System.out;
			out.print("commit[");
			byte[] buf = new byte[1024];
			for (int cb = in.read(buf); cb >= 0; cb = in.read(buf)) {
				out.write(buf, 0, cb);
			}
			out.println("], length = " + this._length);

		}

		private void onLoadTree(InputStream in) throws IOException {

			int mode = 2;

			if (mode == 1) {
				File to = new File("/home/xukun/test/git-tree",
						this._path.getName());
				if (!to.exists()) {
					to.getParentFile().mkdirs();
				}
				OutputStream out = new FileOutputStream(to);
				byte[] buf = new byte[1024];
				for (int cb = in.read(buf); cb > 0; cb = in.read(buf)) {
					out.write(buf, 0, cb);
				}
				System.out.println("            save tree to " + to);
				out.close();

			} else if (mode == 2) {

				for (;;) {
					String str = Tools.readStringUntil(in, 0);
					if (str == null)
						break;
					int i = str.indexOf(' ');
					String mod = str.substring(0, i);
					String name = str.substring(i + 1);
					String id = Tools.readBytesToString(in, 20);

					System.out.println("        tree item: " + id + ", " + mod
							+ ", " + name);

				}
			}

		}
	}

	static class Tools {

		public static String readStringUntil(InputStream in, int ch)
				throws IOException {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int b = in.read(); b != ch; b = in.read()) {
				if (b < 0)
					break;
				baos.write(b);
			}
			if (baos.size() <= 0)
				return null;
			byte[] ba = baos.toByteArray();
			return new String(ba, 0, ba.length, "UTF-8");
		}

		public static String readBytesToString(InputStream in, int cb)
				throws IOException {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < cb; ++i) {

				int b = in.read();

				char c0 = toDigitChar(b >> 4);
				char c1 = toDigitChar(b);

				sb.append(c0);
				sb.append(c1);

			}
			return sb.toString();

		}

		private static char toDigitChar(int i) {
			i = i & 0x0f;
			if (i <= 9) {
				return (char) ('0' + i);
			} else {
				return (char) ('a' + (i - 10));
			}
		}
	}
}
