package ananas.xgitlite.util;

import java.io.File;
import java.io.FileFilter;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLObject;
import ananas.xgitlite.local.LocalObject;
import ananas.xgitlite.local.LocalObjectBank;
import ananas.xgitlite.local.LocalRepo;

public class IndexBuilder implements FileFilter {

	// private final LocalRepo _repo;
	private final LocalObjectBank _obj_bank;

	public IndexBuilder(LocalRepo repo) {
		// this._repo = repo;
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
				System.out.println("add " + id + " << " + path);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	public void begin(File path) {
		// TODO Auto-generated method stub

	}

	public void end() {
		// TODO Auto-generated method stub

	}

}
