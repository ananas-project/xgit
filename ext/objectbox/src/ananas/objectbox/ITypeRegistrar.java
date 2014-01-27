package ananas.objectbox;

import java.util.Enumeration;
import java.util.Properties;

public interface ITypeRegistrar {

	void register(String type, Class<?> cls);

	void register(String type, String cls);

	Class<?> getClass(String type);

	String getType(Class<?> cls);

	class Util {

		public static void loadTypes(ITypeRegistrar reg, Properties prop) {
			Enumeration<Object> enu = prop.keys();
			for (; enu.hasMoreElements();) {
				String key = enu.nextElement().toString();
				String value = prop.getProperty(key);
				reg.register(key.trim(), value.trim());
			}
		}
	}

}
