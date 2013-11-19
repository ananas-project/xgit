package ananas.objectbox;

public interface IObjectCtrl {

	void onCreate(IObject obj);

	void onLoad(IObject obj);

	void onSave(IObject obj);

	IObject getObject();
}
