package ananas.xgitlite.local;

import ananas.xgitlite.util.PathInRepo;

public interface IndexManager {

	IndexList getIndex(PathInRepo path);

}
