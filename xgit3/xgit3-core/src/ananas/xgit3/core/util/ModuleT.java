package ananas.xgit3.core.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ModuleT<T> {

	private T _mod;
	private Object _mod_sync;

	@SuppressWarnings("unchecked")
	protected T getModuleT() {
		T ret = _mod;
		if (ret == null) {
			ret = (T) this.getModuleTS();
			_mod = ret;
		}
		return ret;
	}

	private synchronized Object getModuleTS() {
		try {
			if (_mod_sync != null)
				return _mod_sync;
			Class<?> thisClass = this.getClass();
			String file = thisClass.getSimpleName() + ".properties";
			URL res = thisClass.getResource(file);
			if (res == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("cannot find file in the class path:");
				sb.append(" class=" + thisClass.getName());
				sb.append(" filename=" + file);
				throw new RuntimeException(sb.toString());
			}
			InputStream in = res.openStream();
			Properties prop = new Properties();
			prop.load(in);
			in.close();
			String key = "IMPL";
			String cn = prop.getProperty(key);
			if (cn == null) {
				StringBuilder sb = new StringBuilder();
				sb.append("cannot find key in the properties file:");
				sb.append(" key=" + key);
				sb.append(" file=" + res);
				throw new RuntimeException(sb.toString());
			}
			Class<?> aClass = Class.forName(cn);
			_mod_sync = aClass.newInstance();
			return _mod_sync;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
