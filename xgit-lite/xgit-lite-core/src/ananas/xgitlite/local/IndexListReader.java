package ananas.xgitlite.local;

public interface IndexListReader {

	IndexInfo read();

	void close();
}
