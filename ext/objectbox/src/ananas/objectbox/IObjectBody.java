package ananas.objectbox;

public interface IObjectBody {

	boolean bindHead(IObjectHead head);

	IObjectHead getHead();

	void onCreate();

}
