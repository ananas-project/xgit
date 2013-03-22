package ananas.lib.xgit;

import java.io.InputStream;
import java.util.Properties;

public class RepositoryFactoryLoader {

	private static RepositoryFactory s_factory;

	public static RepositoryFactory getFactory() {
		RepositoryFactory fact = s_factory;
		if (fact == null) {
			fact = (new RepositoryFactoryLoader()).load();
		}
		return fact;
	}

	private RepositoryFactory load() {
		try {
			String file = "xgit.properties";
			Properties prop = new Properties();
			InputStream in = this.getClass().getResourceAsStream(file);
			if (in == null) {
				throw new XGitException("no file: '" + file + "'");
			}
			prop.load(in);
			in.close();
			String classKey = "XGitRepositoryFactory";
			String factClass = prop.getProperty(classKey);
			if (factClass == null) {
				throw new XGitException("no key:" + classKey + " in propfile:"
						+ file);
			}
			return (RepositoryFactory) Class.forName(factClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new XGitException(e);
		}
	}

}
