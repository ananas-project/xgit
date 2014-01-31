package ananas.xgitlite.local;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.util.PathInRepo;

public interface MetaManager {

	MetaInfo getMeta(ObjectId id);

	MetaInfo getMeta(PathInRepo path);

}
