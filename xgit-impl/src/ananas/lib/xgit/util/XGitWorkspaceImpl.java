package ananas.lib.xgit.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileSystem;
import ananas.lib.xgit.XGitFileInfo;
import ananas.lib.xgit.XGitWorkspace;

public class XGitWorkspaceImpl implements XGitWorkspace {

	// private final VFile mMainPath;

	private final Map<String, XGitFileInfo> mInfoTable;
	private final List<String> mKeys;

	public XGitWorkspaceImpl(VFile path) {
		// this.mMainPath = path;

		MyInfoSetFactory isf = new MyInfoSetFactory(path);

		// outer
		isf.add(true, XGitWorkspace.dir_working, true);
		isf.add(true, XGitWorkspace.dir_this, true);
		// inner
		isf.add(true, XGitWorkspace.dir_branches, true);
		isf.add(true, XGitWorkspace.dir_hooks, false);
		isf.add(true, XGitWorkspace.dir_logs, false);
		isf.add(true, XGitWorkspace.dir_objects, true);
		isf.add(true, XGitWorkspace.dir_refs, true);
		isf.add(true, XGitWorkspace.dir_xgit, true);

		isf.add(true, XGitWorkspace.dir_x_tmp, false);

		isf.add(false, XGitWorkspace.file_config, true);
		isf.add(false, XGitWorkspace.file_description, false);
		isf.add(false, XGitWorkspace.file_FETCH_HEAD, false);
		isf.add(false, XGitWorkspace.file_HEAD, false);
		isf.add(false, XGitWorkspace.file_index, false);

		this.mKeys = isf.listKeys();
		this.mInfoTable = isf.genTable();
	}

	private static class MyXGitFileInfo implements XGitFileInfo {

		private final VFile mFile;
		private final boolean mIsDir;
		private final boolean mIsRequired;

		public MyXGitFileInfo(boolean isDir, String key, VFile file,
				boolean isRequired) {

			this.mFile = file;
			this.mIsDir = isDir;
			this.mIsRequired = isRequired;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			sb.append(this.getClass().getSimpleName());
			sb.append(" path:" + this.mFile.getAbsolutePath());
			sb.append(" isDir:" + this.mIsDir);
			sb.append(" isReq:" + this.mIsRequired);
			sb.append(']');
			return sb.toString();
		}

		@Override
		public VFile getFile() {
			return this.mFile;
		}

		@Override
		public boolean isRequired() {
			return this.mIsRequired;
		}

		@Override
		public boolean isDirectory() {
			return this.mIsDir;
		}
	}

	private static class MyInfoSetFactory {

		private final Map<String, XGitFileInfo> m_map;
		private final VFile m_path;

		public MyInfoSetFactory(VFile path) {
			this.m_path = path;
			this.m_map = new Hashtable<String, XGitFileInfo>();
		}

		public Map<String, XGitFileInfo> genTable() {
			return this.m_map;
		}

		public List<String> listKeys() {
			Set<String> keys = this.m_map.keySet();
			List<String> list = new Vector<String>();
			for (String key : keys) {
				list.add(key);
			}
			return list;
		}

		public void add(boolean isDir, String key, boolean isRequired) {

			VFile file = null;

			if (key == null) {
				return;
			} else if (key.equals(XGitWorkspace.dir_working)) {
				file = this.m_path.getParentFile();
			} else if (key.equals(XGitWorkspace.dir_this)) {
				file = this.m_path;
			} else {
				VFileSystem vfs = this.m_path.getVFS();
				file = vfs.newFile(this.m_path, key);
			}

			MyXGitFileInfo info = new MyXGitFileInfo(isDir, key, file,
					isRequired);
			this.add(key, info);
		}

		public void add(String key, XGitFileInfo info) {
			this.m_map.put(key, info);
		}

	}

	@Override
	public VFile getFile(String key) {
		XGitFileInfo info = this.mInfoTable.get(key);
		if (info == null) {
			return null;
		} else {
			return info.getFile();
		}
	}

	@Override
	public XGitFileInfo getFileInfo(String key) {
		return this.mInfoTable.get(key);
	}

	@Override
	public List<String> listKeys() {
		return new ArrayList<String>(this.mKeys);
	}

}
