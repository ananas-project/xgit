package ananas.xgitlite.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGLObject;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;
import ananas.xgitlite.local.LocalRepo;

public class IndexBuilder implements FileFilter {

	private final LocalRepo _repo;
	private final LocalObjectBank _obj_bank;
	private File _index_root;

	public IndexBuilder(LocalRepo repo) throws IOException, XGLException {
		this._repo = repo;
		this._obj_bank = repo.getObjectBank();
	}

	@Override
	public boolean accept(File path) {

		if (path.isDirectory()) {

			// directory

		} else {

			// file

			try {

				LocalObject obj = _obj_bank
						.addObject(XGLObject.Type.blob, path);
				ObjectId id = obj.getId();

				FileMeta meta = new FileMeta();
				meta.path = Helper.getOffsetPath(_index_root, path);
				meta.length = obj.getLength();
				meta.type = obj.getType();
				meta.id = id.toString();

				System.out.println("add " + MyHelper.toString(meta));

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	/**
	 * @param path
	 *            the index root in the repo
	 * @throws IOException
	 * @throws XGLException
	 * */
	public void begin(File path) throws XGLException, IOException {
		// TODO Auto-generated method stub

		this._index_root = path;
		String indexPath = this.toRepoBasedPath(path);
		System.out.println("begin index in [" + indexPath + "]");

	}

	public void end() {
		// TODO Auto-generated method stub

	}

	private String toRepoBasedPath(File path) throws XGLException, IOException {
		File wkdir = this._repo.getWorkingDirectory();
		return Helper.getOffsetPath(wkdir, path);
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
