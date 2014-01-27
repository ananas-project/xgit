package ananas.xgitlite;

public interface LocalRepo extends Repo {

	LocalObject
	getObject  ( ObjectId id ) ;
	
}
