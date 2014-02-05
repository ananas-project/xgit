package ananas.xgitlite.xmail;

public class DefaultXMailFactory extends XMailFactoryWrapper {

	public DefaultXMailFactory() {
		super(initFactory());
	}

	private static XMailFactory initFactory() {
		try {
			String className = "ananas.xgitlite.xmail.impl.XMailFactoryImpl";
			Class<?> aClass = Class.forName(className);
			return (XMailFactory) aClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
