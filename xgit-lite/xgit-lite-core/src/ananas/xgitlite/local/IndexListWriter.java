package ananas.xgitlite.local;

import java.io.IOException;

public interface IndexListWriter {

	void write(IndexInfo info) throws IOException;

	void close() throws IOException;

}
