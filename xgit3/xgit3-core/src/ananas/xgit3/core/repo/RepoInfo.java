package ananas.xgit3.core.repo;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public interface RepoInfo {

	boolean isBare();

	boolean isRemote();

	boolean isFileMode();

	URI getURI();

	RepoDriver getDriver();

	RepoContext getContext();

	String getParameter(String key);

	Map<String, String> getParameters(Set<String> keys);

}
