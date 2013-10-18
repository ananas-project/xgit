package ananas.lib.xgit.util;

import ananas.xgit.repo.ObjectId;
import ananas.xgit.repo.ObjectIdFactory;

public class ObjectIdFactoryImpl implements ObjectIdFactory {

	@Override
	public ObjectId create(String s) {
		return new ObjectIdImpl(s);
	}

	private final static char[] chs = "0123456789abcdef".toCharArray();

	@Override
	public ObjectId create(byte[] ba) {
		StringBuilder sb = new StringBuilder();
		for (byte b : ba) {
			sb.append(chs[0x0f & (b >> 4)]);
			sb.append(chs[0x0f & b]);
		}
		return new ObjectIdImpl(sb.toString());
	}

	static class ObjectIdImpl implements ObjectId {

		private final String _string;

		public ObjectIdImpl(String s) {
			_string = s;
		}

		public String toString() {
			return _string;
		}
	}

}
