package ananas.objectbox;

public interface IObjectIOManager {

	IObjectLoader getLoader(Class<?> cls);

	IObjectSaver getSaver(Class<?> cls);

	void register(Class<?> cls, IObjectLS ls);

}
