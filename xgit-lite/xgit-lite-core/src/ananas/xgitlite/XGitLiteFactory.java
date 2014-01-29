package ananas.xgitlite;

public class XGitLiteFactory {

	public synchronized static XGitLite getXGitLite() {

		try {
			// String key = XGitLite.class.getName() ;
			String value = "ananas.xgitlite.impl.XGitLiteImplementation";

			Class<?> aClass = Class.forName(value);
			return (XGitLite) aClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
