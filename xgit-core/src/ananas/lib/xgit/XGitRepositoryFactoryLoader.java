package ananas.lib.xgit;

import java.io.InputStream;
import java.util.Properties;

public class XGitRepositoryFactoryLoader {

	private static XGitRepositoryFactory s_factory;

	public static XGitRepositoryFactory getFactory() {
		XGitRepositoryFactory fact = s_factory;
		if (fact == null) {
			fact = (new XGitRepositoryFactoryLoader()).load();
		}
		return fact;
	}

	private XGitRepositoryFactory load() {
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
			return (XGitRepositoryFactory) Class.forName(factClass)
					.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new XGitException(e);
		}
	}

}
