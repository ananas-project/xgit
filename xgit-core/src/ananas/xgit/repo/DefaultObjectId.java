package ananas.xgit.repo;

import ananas.lib.util.SingletonLoader;

public class DefaultObjectId implements ObjectId {

	private static ObjectIdFactory _factory;

	private static ObjectIdFactory __get_factory() {
		ObjectIdFactory fact = _factory;
		if (fact == null) {
			fact = (ObjectIdFactory) SingletonLoader
					.load(ObjectIdFactory.class);
			_factory = fact;
		}
		return fact;
	}

	private final ObjectId _inner;

	public DefaultObjectId(String s) {
		_inner = __get_factory().create(s);
	}

	public DefaultObjectId(byte[] ba) {
		_inner = __get_factory().create(ba);
	}

	@Override
	public String toString() {
		return _inner.toString();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		String s1 = this.toString();
		String s2 = obj.toString();
		return s1.equals(s2);
	}

}
