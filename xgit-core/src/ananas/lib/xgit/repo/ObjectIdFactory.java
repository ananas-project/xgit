package ananas.lib.xgit.repo;

public interface ObjectIdFactory {

	ObjectId create(String s);

	ObjectId create(byte[] ba);
}
