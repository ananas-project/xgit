package ananas.xgitlite.remote;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.Repo;

public interface RemoteRepo extends Repo {

	
	 RemoteObject  getObject ( ObjectId id ) ;
	
}
