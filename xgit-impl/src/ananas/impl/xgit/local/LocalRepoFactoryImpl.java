package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import ananas.lib.io.vfs.VFS;
import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.juke.Kernel;
import ananas.xgit.XGitException;
import ananas.xgit.repo.Repo;
import ananas.xgit.repo.local.LocalRepo;
import ananas.xgit.repo.local.LocalRepoFactory;

public class LocalRepoFactoryImpl implements LocalRepoFactory {

	interface Config {

		String bare = "bare";
		String xgit = "xgit";
		String repositoryformatversion = "repositoryformatversion";
		String filemode = "filemode";
		String logallrefupdates = "logallrefupdates";

	}

	static class PropHelper {

		public static boolean getBoolean(Properties prop, String key) {
			String value = prop.getProperty(key);
			if (value == null)
				return false;
			return value.trim().equalsIgnoreCase("true");
		}

		public static int getInt(Properties prop, String key) {
			String s = prop.getProperty(key);
			if (s == null)
				return 0;
			return Integer.parseInt(s);
		}

		public static String getString(Properties prop, String key) {
			String s = prop.getProperty(key);
			if (s == null)
				return s;
			return s.trim();
		}
	}

	interface RepoTempItem {

		boolean dir = true;
		boolean file = false;

		boolean isDir();

		boolean isReqire();

		VFile getPath();

		void doInit() throws IOException;

		void doCheck() throws XGitException;

	}

	class RepoTemp {

		private final List<RepoTempItem> _list = new ArrayList<RepoTempItem>();
		private final VFile _repo_dir;
		private final VFileSystem _vfs;

		RepoTemp(VFile repo_dir) {

			this._repo_dir = repo_dir;
			this._vfs = _repo_dir.getVFS();

			addItem("branches", RepoTempItem.dir, true);
			addItem("objects", RepoTempItem.dir, true);
			addItem("refs", RepoTempItem.dir, true);
			addItem("config", RepoTempItem.file, true);
			addItem("HEAD", RepoTempItem.file, true);

			addItem("xgit", RepoTempItem.dir, false);
			addItem("info", RepoTempItem.dir, false);
			addItem("logs", RepoTempItem.dir, false);
			addItem("description", RepoTempItem.file, false);

		}

		private void addItem(String name, boolean isdir, boolean reqired) {
			VFile path = _vfs.newFile(_repo_dir, name);
			RepoTempItem item = new MyRepoTempItem(path, isdir, reqired);
			_list.add(item);
		}
	}

	static class MyRepoTempItem implements RepoTempItem {

		private final VFile _path;
		private final boolean _is_dir;
		private final boolean _reqired;

		public MyRepoTempItem(VFile path, boolean isdir, boolean reqired) {
			_path = path;
			_is_dir = isdir;
			_reqired = reqired;
		}

		@Override
		public boolean isDir() {
			return _is_dir;
		}

		@Override
		public boolean isReqire() {
			return _reqired;
		}

		@Override
		public VFile getPath() {
			return _path;
		}

		@Override
		public void doInit() throws IOException {

			if (_is_dir) {

				_path.mkdirs();

			} else {
				VFile dir = _path.getParentFile();
				if (!dir.exists())
					dir.mkdirs();
				_path.createNewFile();
			}

		}

		@Override
		public void doCheck() throws XGitException {

			if (_path.exists()) {
				if (_path.isDirectory() != _is_dir) {
					throw new XGitException(
							"the node must be(or not be) a directory : "
									+ _path);
				}
			} else {
				if (this._reqired) {
					throw new XGitException("the node must be exists : "
							+ _path);
				}
			}

		}
	}

	@Override
	public Repo openRepo(Kernel kernel, URI uri) throws IOException,
			XGitException {
		VFileSystem vfs = VFS.getDefaultFactory().defaultFileSystem()
				.getFactory().defaultFileSystem();
		return this.openRepo(kernel, vfs.newFile(uri));
	}

	@Override
	public LocalRepo openRepo(Kernel kernel, VFile dir) throws IOException,
			XGitException {

		VFile conf = dir.getVFS().newFile(dir, "config");

		Properties prop = new Properties();
		InputStream in = new VFileInputStream(conf);
		prop.load(in);
		in.close();

		System.out.println("open xgit repo at " + dir);
		Set<Object> keys = prop.keySet();
		for (Object k : keys) {
			String key = k.toString();
			String value = prop.getProperty(key);
			System.out.println("        " + key + " = " + value);
		}

		boolean bare = PropHelper.getBoolean(prop, Config.bare);
		boolean xgit = PropHelper.getBoolean(prop, Config.xgit);
		String fmtVer = PropHelper.getString(prop,
				Config.repositoryformatversion);

		RepoTemp temp = new RepoTemp(dir);
		for (RepoTempItem item : temp._list) {
			item.doCheck();
		}

		if (!"0".equals(fmtVer)) {
			throw new XGitException("it's not a git repo : " + dir);
		}
		if (!xgit) {
			throw new XGitException("it's a git but not a xgit repo : " + dir);
		}

		if (kernel == null) {
			kernel = Kernel.Factory.create();
		}

		// do open
		LocalRepo repo = new LocalRepoImpl(kernel, this, dir, bare);
		return repo;
	}

	@Override
	public LocalRepo initRepo(Kernel kernel, VFile dir, boolean bare)
			throws XGitException, IOException {

		if (dir.exists()) {
			throw new XGitException("the repo directory is exists : " + dir);
		}

		if (!bare) {
			if (!dir.getName().equals(".git")) {
				throw new XGitException(
						"the non-bare repo directory name must be '.git' : "
								+ dir);
			}
			VFile parent = dir.getParentFile();
			if (parent.exists()) {
				throw new XGitException("the working directory is exists : "
						+ parent);
			}
		}

		RepoTemp temp = new RepoTemp(dir);
		for (RepoTempItem item : temp._list) {
			item.doInit();
		}

		Properties prop = new Properties();
		{
			prop.setProperty(Config.bare, bare + "");
			prop.setProperty(Config.xgit, true + "");
			prop.setProperty(Config.filemode, true + "");
			prop.setProperty(Config.repositoryformatversion, 0 + "");
			prop.setProperty(Config.logallrefupdates, true + "");
		}
		VFile conf_file = dir.getVFS().newFile(dir, "config");
		OutputStream out = new VFileOutputStream(conf_file);
		prop.store(out, "xgit config");
		out.close();

		if (kernel == null) {
			kernel = Kernel.Factory.create();
		}

		LocalRepo repo = new LocalRepoImpl(kernel, this, dir, bare);
		System.out.println("init xgit repo at " + dir);
		return repo;
	}

}
