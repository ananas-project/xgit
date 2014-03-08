package impl.ananas.xgit3.core.local.tree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObject;
import ananas.xgit3.core.local.LocalObjectBank;
import ananas.xgit3.core.local.LocalRepo;
import ananas.xgit3.core.local.ext.FileTracker;
import ananas.xgit3.core.local.ext.FileTracking;

public class TreeBuilder {

	private LocalObject _root;
	// private final LocalRepo _repo;
	private final FileTracker _tracker;
	private final LocalObjectBank _bank;

	public TreeBuilder(LocalRepo repo) {
		// this._repo = repo;
		this._tracker = repo.getXGitExtends().getFileTracker();
		this._bank = repo.getBank();

	}

	public TreeNodeHandler getNodeHandler() {
		return this._handler;
	}

	public LocalObject getRoot() {
		return this._root;
	}

	private final TreeNodeHandler _handler = new TreeNodeHandler() {

		private DirNode _node_stack_ptr;
		private DirNode _root;

		@Override
		public void onWalkBegin(File root) {
			System.out.println(this + ".walkBegin, root:" + root);
			this._node_stack_ptr = null;
		}

		@Override
		public void onDirBegin(File root, File dir) {
			// System.out.println("onDirBegin: " + dir);
			DirNode dn = new DirNode();
			this.node_push(dn);
		}

		@Override
		public void onFile(File root, File file) {
			try {
				// System.out.println("onFile:     " + file);
				StringBuilder sb = new StringBuilder();
				final TreeBuilder pthis = TreeBuilder.this;
				FileTracking tracking = pthis._tracker.getTracking(file);
				final LocalObject obj;
				if (tracking.isTracked()) {
					sb.append("exists ");
					obj = pthis._bank.get(tracking.id());
				} else {
					sb.append("add    ");
					obj = pthis._bank.add("blob", file);
					tracking.doTrack(obj.id(), file.lastModified(),
							file.length());
				}
				final char tab = '\t';
				sb.append(tab);
				sb.append(obj.id());
				sb.append(tab);
				sb.append(obj.type());
				sb.append(tab);
				sb.append(obj.length());
				sb.append(tab);
				sb.append(file.getAbsolutePath().substring(
						root.getAbsolutePath().length() + 1));
				// System.out.println(sb);
				Node node = new Node();
				node.name = file.getName();
				node.id = obj.id();
				node.mode = Helper.getFileMode(file);
				this.node_add(node);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void onDirEnd(File root, File dir) {
			try {
				// System.out.println("onDirEnd:   " + dir);
				DirNode child = this.node_pop();
				DirNode parent = this.node_peek();
				child.name = dir.getName();
				child.mode = Helper.getFileMode(dir);
				child.addToBank(TreeBuilder.this._bank);
				if (parent == null) {
					this._root = child;
				} else {
					parent.addChild(child);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void onWalkEnd(File root) {
			System.out.println(this + ".walkEnd, root:" + root);
			TreeBuilder pthis = TreeBuilder.this;
			LocalObject obj = pthis._bank.get(this._root.id);
			pthis._root = obj;
		}

		private void node_add(Node node) {
			DirNode dir = this._node_stack_ptr;
			if (dir != null) {
				dir.addChild(node);
			}
		}

		private void node_push(DirNode dir) {
			dir.parent = this._node_stack_ptr;
			this._node_stack_ptr = dir;
		}

		private DirNode node_pop() {
			DirNode child = this._node_stack_ptr;
			this._node_stack_ptr = child.parent;
			return child;
		}

		private DirNode node_peek() {
			return this._node_stack_ptr;
		}

	};

	static class Helper {

		public static int getFileMode(File file) {
			if (file.isDirectory()) {
				return LocalObject.mode_dir;
			} else {
				if (file.canExecute()) {
					return LocalObject.mode_exe;
				} else {
					return LocalObject.mode_file;
				}
			}
		}
	}

	static class Node {
		DirNode parent;
		public HashID id;
		public int mode;
		public String name;
	}

	static class FileNode extends Node {
	}

	static class DirNode extends Node {

		private final Map<String, HashID> chs;

		public DirNode() {
			chs = new HashMap<String, HashID>();
		}

		public void addChild(Node node) {
			StringBuilder sb = new StringBuilder();
			sb.append(node.mode);
			sb.append(' ');
			sb.append(node.name);
			chs.put(sb.toString(), node.id);
		}

		public void addToBank(LocalObjectBank bank) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
			Writer wtr = new OutputStreamWriter(baos, "UTF-8");
			Set<String> keys = chs.keySet();
			String[] array = keys.toArray(new String[keys.size()]);
			Arrays.sort(array);
			System.out.println("tree {");
			for (String key : array) {
				HashID ch_id = chs.get(key);
				System.out.println(ch_id + " " + key);
				wtr.append(key);
				wtr.flush();
				baos.write(0);
				baos.write(ch_id.toByteArray());
			}
			baos.flush();
			byte[] ba = baos.toByteArray();
			LocalObject obj = bank.add("tree", ba.length,
					new ByteArrayInputStream(ba));
			this.id = obj.id();
			System.out.println("} tree " + id);
		}
	}

}
