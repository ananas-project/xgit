package ananas.lib.xgit;

public interface MonitorRegistrar {

	NodeMonitor getMonitor(Class<?> apiClass);

	void registerMonitor(NodeMonitor monitor);

}
