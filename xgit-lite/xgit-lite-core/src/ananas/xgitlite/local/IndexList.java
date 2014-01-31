package ananas.xgitlite.local;

import java.io.IOException;

public interface IndexList {

	IndexListWriter openWriter() throws IOException;

	IndexListReader openReader();
}
