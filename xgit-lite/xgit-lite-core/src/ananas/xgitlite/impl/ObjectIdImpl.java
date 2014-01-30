package ananas.xgitlite.impl;

import java.util.Arrays;

import ananas.xgitlite.ObjectId;

class ObjectIdImpl implements ObjectId {

	private final int n0;
	private final int n1;
	private final int n2;
	private final int n3;
	private final int n4;

	// private byte[] _bytes = null;
	private String _string = null;

	public ObjectIdImpl(int _n0, int _n1, int _n2, int _n3, int _n4) {
		n0 = _n0;
		n1 = _n1;
		n2 = _n2;
		n3 = _n3;
		n4 = _n4;
	}

	public static ObjectId create(byte[] id) {
		try {

			final int step = 4;
			int i = 0;
			int n0, n1, n2, n3, n4;
			n0 = Helper.parseBytes(id, (i++) * step, step);
			n1 = Helper.parseBytes(id, (i++) * step, step);
			n2 = Helper.parseBytes(id, (i++) * step, step);
			n3 = Helper.parseBytes(id, (i++) * step, step);
			n4 = Helper.parseBytes(id, (i++) * step, step);

			return new ObjectIdImpl(n0, n1, n2, n3, n4);

		} catch (Exception e) {
			throw new NumberFormatException("bad id string format: "
					+ Arrays.toString(id));
		}
	}

	public static ObjectId create(String id) {

		try {
			char[] chs = id.toCharArray();

			int step = 8;
			int i = 0;
			int n0, n1, n2, n3, n4;
			n0 = Helper.parseString(chs, (i++) * step, step);
			n1 = Helper.parseString(chs, (i++) * step, step);
			n2 = Helper.parseString(chs, (i++) * step, step);
			n3 = Helper.parseString(chs, (i++) * step, step);
			n4 = Helper.parseString(chs, (i++) * step, step);

			return new ObjectIdImpl(n0, n1, n2, n3, n4);

		} catch (Exception e) {
			throw new NumberFormatException("bad id string format: " + id);
		}
	}

	public String toString() {
		String str = _string;
		if (str == null) {
			StringBuilder sb = new StringBuilder(50);
			Helper.toString(sb, n0);
			Helper.toString(sb, n1);
			Helper.toString(sb, n2);
			Helper.toString(sb, n3);
			Helper.toString(sb, n4);
			_string = str = sb.toString();
		}
		return str;
	}

	static class Helper {

		public static void toString(StringBuilder sb, final int n) {
			for (int i = 0; i < 32; i += 4) {
				final int x = (n >> i) & 0x0f;
				char ch = (char) ((x < 10) ? ('0' + x) : ('a' + (x - 10)));
				sb.append(ch);
			}
		}

		public static int parseBytes(byte[] id, int offset, int length) {
			int n = 0;
			final int end = offset + length;
			for (int i = end - 1; i >= offset; i--) {
				n <<= 8;
				n |= ((id[i] >> 4) & 0x000f);
				n |= ((id[i] << 4) & 0x00f0);
			}
			return n;
		}

		public static int parseString(char[] chs, int offset, int length) {

			int n = 0;
			for (int i = length - 1; i >= 0; --i) {
				char ch = chs[offset + i];

				// System.out.print("  " + ch + "  ");

				int x = 0;
				if ('0' <= ch && ch <= '9') {
					x = ch - '0';
				} else if ('a' <= ch && ch <= 'f') {
					x = (ch - 'a') + 10;
				} else if ('A' <= ch && ch <= 'F') {
					x = (ch - 'A') + 10;
				} else {
					throw new RuntimeException("bad char: " + ch);
				}
				n <<= 4;
				n |= (x & 0x0f);
			}
			return n;
		}

	}

	/*
	 * 
	 * @Override public byte[] getBytes() { byte[] ba = _bytes; if (ba == null)
	 * { ByteArrayOutputStream baos = new ByteArrayOutputStream(25);
	 * Helper.toBytes(baos, n0); Helper.toBytes(baos, n1); Helper.toBytes(baos,
	 * n2); Helper.toBytes(baos, n3); Helper.toBytes(baos, n4); _bytes = ba =
	 * baos.toByteArray(); } return ba; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n0;
		result = prime * result + n1;
		result = prime * result + n2;
		result = prime * result + n3;
		result = prime * result + n4;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectIdImpl other = (ObjectIdImpl) obj;

		if (n0 != other.n0)
			return false;
		if (n1 != other.n1)
			return false;
		if (n2 != other.n2)
			return false;
		if (n3 != other.n3)
			return false;
		if (n4 != other.n4)
			return false;

		return true;
	}

}
