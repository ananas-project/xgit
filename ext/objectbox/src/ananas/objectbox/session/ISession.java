package ananas.objectbox.session;

import java.util.Map;

import ananas.xgit.repo.ObjectId;

public interface ISession {

	IElement getElement(ObjectId id);

	IElement createElement(Class<?> wrapperClass, Map<String, String> header);

	ISession newSession();

	void save();
}
