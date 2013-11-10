package ananas.impl.xgit.local.indexer.def;

import java.io.IOException;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.IndexNode;
import ananas.xgit.repo.local.Indexer;

public class ExtIndexNodeImpl implements IndexNode {

	private final VFile _target_file;
	private final VFile _meta_file;
	private final Meta _meta;
	private final Indexer _indexer;

	public ExtIndexNodeImpl(Indexer indexer, VFile target, VFile meta) {
		this._indexer = indexer;
		this._target_file = target;
		this._meta_file = meta;
		this._meta = new Meta(meta);
	}

	@Override
	public VFile getTargetFile() {
		return this._target_file;
	}

	@Override
	public ObjectId getObjectId() {
		return this._meta.id();
	}

	private Meta __get_meta() {
		// TODO Auto-generated method stub
		return null;
	}

	class Meta {

		private final VFile _file;
		private long _last_modified;
		private long _length;
		private ObjectId _hash_id;

		public Meta(VFile file) {
			this._file = file;
		}

		public ObjectId id() {
			return this._hash_id;
		}

		public long length() {
			return this._length;
		}

		public void save() {
			// TODO Auto-generated method stub

		}

		public void load() {
			// TODO Auto-generated method stub

		}

		public void update(long lastModif, ObjectId id, long length) {
			this._length = length;
			this._last_modified = lastModif;
			this._hash_id = id;
		}

		public long lastModified() {
			return this._last_modified;
		}
	}

	@Override
	public Indexer getIndexer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(boolean r) throws IOException {
		// TODO Auto-generated method stub

	}
}
