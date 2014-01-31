package ananas.xgitlite.impl;

import java.io.File;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.local.IndexList;
import ananas.xgitlite.local.IndexManager;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.MetaInfo;
import ananas.xgitlite.local.MetaManager;
import ananas.xgitlite.util.PathInRepo;

final class MetaIndexManagerImpl implements MetaManager, IndexManager {

	private final File _base_id;
	private final File _base_path;

	public MetaIndexManagerImpl(LocalRepo repo) {

		File rpdir = repo.getRepoDirectory();
		StringBuilder sb = new StringBuilder();
		char sp = File.separatorChar;
		{
			// for id
			sb.append(LocalRepo.Name.xgit_dir);
			sb.append(sp);
			sb.append(".meta");
			sb.append(sp);
			sb.append("id");
			this._base_id = new File(rpdir, sb.toString());
		}
		sb.setLength(0);
		{
			// for path
			sb.append(LocalRepo.Name.xgit_dir);
			sb.append(sp);
			sb.append(".meta");
			sb.append(sp);
			sb.append("path");
			this._base_path = new File(rpdir, sb.toString());
		}
	}

	@Override
	public MetaInfo getMeta(ObjectId id) {

		int sp = 2;
		String str = id.toString();
		String s1 = str.substring(0, sp);
		String s2 = str.substring(sp);

		StringBuilder sb = new StringBuilder();
		sb.append('x');
		sb.append(s1);
		sb.append(File.separatorChar);
		sb.append('x');
		sb.append(s2);
		sb.append(".meta");

		File file = new File(this._base_id, sb.toString());
		return new MetaInfoImpl(file);
	}

	@Override
	public MetaInfo getMeta(PathInRepo path) {
		StringBuilder sb = new StringBuilder();
		sb.append(path.getPath());
		sb.append(".meta");

		File file = new File(this._base_path, sb.toString());
		return new MetaInfoImpl(file);
	}

	@Override
	public IndexList getIndex(PathInRepo path) {

		StringBuilder sb = new StringBuilder();
		sb.append(path.getPath());
		sb.append(".index");

		File file = new File(this._base_path, sb.toString());
		return new IndexListImpl(file);
	}

}
