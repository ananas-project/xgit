package ananas.objectbox.session;

public interface IWrapper {

	void onLoad(IElement element);

	void onSave(IElement element);

	IElement getTarget();

}
