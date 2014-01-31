package ananas.xgitlite.local;

import java.io.IOException;
import java.util.Properties;

public interface MetaInfo {

	interface Key {

		String git_type = "git.type";
		String git_length = "git.length";
		String git_id = "git.id";
		String git_last_mod = "git.last_modified";

		String content_type = "Content-Type";

	}

	// getter

	Properties getProperties();

	long getLong(String key);

	String getString(String key);

	long getLastModified();

	long getLength();

	String getId();

	// setter

	void setLong(String key, long value);

	void setString(String key, String value);

	void setLastModified(long lm);

	void setLength(long length);

	void setId(String id);

	// I/O

	boolean load() throws IOException;

	void save() throws IOException;

}
