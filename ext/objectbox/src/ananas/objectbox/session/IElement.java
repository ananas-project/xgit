package ananas.objectbox.session;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface IElement {

	ISession getSession();

	ObjectId getId();

	Class<?> getWrapperClass();

	IWrapper getWrapper();

	boolean isModified();

	void setModified(boolean isMod);

	void save();

	void load();

	VFile getBodyFile();

	String[] getHeaderNames();

	String getHeader(String name);
}
