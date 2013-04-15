package ananas.lib.xgit.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ananas.lib.xgit.BaseRepository;
import ananas.lib.xgit.NodeMonitor;
import ananas.lib.xgit.RepositoryDirectory;
import ananas.lib.xgit.XGitEnvironment;

public class BaseRepositoryImpl implements BaseRepository {

	private final XGitEnvironment mEnvi;
	private final boolean mIsBare;
	private final Map<Class<?>, NodeMonitor> mMap;
	private RepositoryDirectory mRepoDir;

	public BaseRepositoryImpl(XGitEnvironment envi, boolean bare) {
		this.mEnvi = envi;
		this.mIsBare = bare;
		this.mMap = new HashMap<Class<?>, NodeMonitor>();
	}

	@Override
	public NodeMonitor getMonitor(Class<?> apiClass) {
		return this.mMap.get(apiClass);
	}

	@Override
	public void registerMonitor(NodeMonitor monitor) {
		List<Class<?>> list = this.getAPIs(monitor.getClass());
		for (Class<?> api : list) {
			this.mMap.put(api, monitor);
		}
	}

	private List<Class<?>> getAPIs(Class<?> cls) {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		for (; cls != null; cls = cls.getSuperclass()) {
			Class<?>[] apis = cls.getInterfaces();
			for (Class<?> api : apis) {

				list.add(api);
			}
		}
		return list;
	}

	@Override
	public boolean isBare() {
		return this.mIsBare;
	}

	@Override
	public XGitEnvironment getEnvironment() {
		return this.mEnvi;
	}

	@Override
	public RepositoryDirectory getRepoDirectory() {
		RepositoryDirectory ret = this.mRepoDir;
		if (ret == null) {
			ret = (RepositoryDirectory) this
					.getMonitor(RepositoryDirectory.class);
			this.mRepoDir = ret;
		}
		return ret;
	}

}
