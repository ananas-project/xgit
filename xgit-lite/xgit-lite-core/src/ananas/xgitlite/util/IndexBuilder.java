package ananas.xgitlite.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGLObject;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.IndexList;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.MetaInfo;

public class IndexBuilder implements FileFilter {

	private final LocalRepo _repo;
	private PathInRepo _index_base;
	private IndexList _index;

	// private File _index_root;

	public IndexBuilder(LocalRepo repo) {
		this._repo = repo;
	}

	@Override
	public boolean accept(File path) {

		if (path.isDirectory()) {

			// directory

		} else {

			// file

			try {

				/*
				 * 
				 * LocalObject obj = _obj_bank .addObject(XGLObject.Type.blob,
				 * path); ObjectId id = obj.getId();
				 * 
				 * FileMeta meta = new FileMeta(); meta.path =
				 * Helper.getOffsetPath(_index_root, path); meta.length =
				 * obj.getLength(); meta.type = obj.getType(); meta.id =
				 * id.toString();
				 * 
				 * System.out.println("add " + MyHelper.toString(meta));
				 */

				this.accept_file(path);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	private void accept_file(File path) throws IOException, XGLException {

		final PathInRepo pir = PathInRepo.newInstance(_repo, path);
		final MetaInfo meta = _repo.getMetaManager().getMeta(pir);

		ObjectId id = null;
		if (meta.load()) {

			long lmd1 = meta.getLastModified();
			long len1 = meta.getLength();
			long lmd2 = path.lastModified();
			long len2 = path.length();

			if ((lmd1 == lmd2) && (len1 == len2)) {
				String idstr = meta.getId();
				if (idstr != null) {
					id = XGitLite.getInstance().createObjectId(idstr);
				}
			}
		}
		if (id != null) {
			LocalObject obj = _repo.getObjectBank().getObject(id);
			if (!obj.exists()) {
				id = null;
			}
		}

		if (id == null) {
			// re-hash
			LocalObject obj = _repo.getObjectBank().addObject(
					XGLObject.Type.blob, path);
			id = obj.getId();
			meta.setId(id.toString());
			meta.setLastModified(path.lastModified());
			meta.setLength(path.length());
			meta.save();
			System.out.println("re-hash id:" + id + " path:" + pir.getPath());
		}

	}

	/**
	 * @param path
	 *            the index root in the repo
	 * @throws IOException
	 * @throws XGLException
	 * */
	public void begin(File path) throws XGLException, IOException {

		PathInRepo pir = PathInRepo.newInstance(this._repo, path);
		this._index_base = pir;
		this._index = _repo.getIndexManager().getIndex(pir);

	}

	public void end() {
		// TODO Auto-generated method stub

	}

	static class MyHelper {

		public static String toString(FileMeta meta) {
			StringBuilder sb =

			new StringBuilder();

			sb.append(" id:");
			sb.append(meta.id);

			sb.append(" type:");
			sb.append(meta.type);

			sb.append(" path:");
			sb.append(meta.path);

			return sb.toString();
		}
	}

}
