package ananas.objectbox;

public interface IObjectIOManager {

	IObjectLoader getLoader(Class<?> cls);

	IObjectSaver getSaver(Class<?> cls);

	IObjectLoader registerLoader(Class<?> cls);

	IObjectSaver registerSaver(Class<?> cls);

}
