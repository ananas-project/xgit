package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileInputStream;
import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.local.ExtIndexInfo;
import ananas.xgit.repo.local.LocalObject;
import ananas.xgit.repo.local.LocalRepo;

public class ExtIndexInfoImpl implements ExtIndexInfo {

	private final LocalRepo _repo;
	private final VFile _target_file;
	private final VFile _meta_file;
	private final Meta _meta;

	public ExtIndexInfoImpl(LocalRepo repo, VFile target, VFile meta) {
		this._repo = repo;
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

	@Override
	public void add() throws IOException {

		System.out.println("add " + _target_file);

		Meta meta = __get_meta();
		VFile tar = _target_file;
		if (tar.exists()) {
			long len1, len2, lm1, lm2;
			len1 = tar.length();
			lm1 = tar.lastModified();
			len2 = meta.length();
			lm2 = meta.lastModified();
			if (len1 == len2 && lm1 == lm2) {
				// not modify
				return;
			}

			InputStream in = new VFileInputStream(tar);
			LocalObject go = _repo.getObjectBank().addObject("blob", len1, in);
			in.close();

			meta.update(lm1, go.id(), len1);
			meta.save();

		} else {
			// delete meta
		}

	}

	private Meta __get_meta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

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
}
