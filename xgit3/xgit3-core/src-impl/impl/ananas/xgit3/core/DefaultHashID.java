package impl.ananas.xgit3.core;

import java.util.Arrays;

import ananas.xgit3.core.HashID;

final class DefaultHashID {

	public static HashID create(String s) {
		int len = s.length();
		if (len == 40) {
			TheSha1 impl = new TheSha1();
			char[] ch = s.toCharArray();
			final int step = 8;
			impl.n0 = Parser.parse32(ch, (0 * step), step);
			impl.n1 = Parser.parse32(ch, (1 * step), step);
			impl.n2 = Parser.parse32(ch, (2 * step), step);
			impl.n3 = Parser.parse32(ch, (3 * step), step);
			impl.n4 = Parser.parse32(ch, (4 * step), step);
			return impl;
		} else if (len == 64) {
			TheSha256 impl = new TheSha256();
			char[] ch = s.toCharArray();
			final int step = 16;
			impl.n0 = Parser.parse64(ch, (0 * step), step);
			impl.n1 = Parser.parse64(ch, (1 * step), step);
			impl.n2 = Parser.parse64(ch, (2 * step), step);
			impl.n3 = Parser.parse64(ch, (3 * step), step);
			return impl;
		} else {
			throw new RuntimeException("bad id format: " + s);
		}
	}

	public static HashID create(byte[] b) {
		int len = b.length;
		if (len == 20) {
			TheSha1 impl = new TheSha1();
			final int step = 4;
			impl.n0 = Parser.parse32(b, (0 * step), step);
			impl.n1 = Parser.parse32(b, (1 * step), step);
			impl.n2 = Parser.parse32(b, (2 * step), step);
			impl.n3 = Parser.parse32(b, (3 * step), step);
			impl.n4 = Parser.parse32(b, (4 * step), step);
			return impl;
		} else if (len == 32) {
			TheSha256 impl = new TheSha256();
			final int step = 8;
			impl.n0 = Parser.parse64(b, (0 * step), step);
			impl.n1 = Parser.parse64(b, (1 * step), step);
			impl.n2 = Parser.parse64(b, (2 * step), step);
			impl.n3 = Parser.parse64(b, (3 * step), step);
			return impl;
		} else {
			throw new RuntimeException("bad id format: " + Arrays.toString(b));
		}
	}

	static class TheSha1 implements HashID {

		private int n0;
		private int n1;
		private int n2;
		private int n3;
		private int n4;

		@Override
		public byte[] toByteArray() {
			byte[] ba = new byte[20];
			final int step = 4;
			Seri.toByteArray32(n0, ba, (0 * step), step);
			Seri.toByteArray32(n1, ba, (1 * step), step);
			Seri.toByteArray32(n2, ba, (2 * step), step);
			Seri.toByteArray32(n3, ba, (3 * step), step);
			Seri.toByteArray32(n4, ba, (4 * step), step);
			return ba;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Seri.toString32(n0, sb);
			Seri.toString32(n1, sb);
			Seri.toString32(n2, sb);
			Seri.toString32(n3, sb);
			Seri.toString32(n4, sb);
			return sb.toString();
		}

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
			TheSha1 other = (TheSha1) obj;
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

	static class TheSha256 implements HashID {

		private long n0;
		private long n1;
		private long n2;
		private long n3;

		@Override
		public byte[] toByteArray() {
			byte[] ba = new byte[32];
			final int step = 8;
			Seri.toByteArray64(n0, ba, (0 * step), step);
			Seri.toByteArray64(n1, ba, (1 * step), step);
			Seri.toByteArray64(n2, ba, (2 * step), step);
			Seri.toByteArray64(n3, ba, (3 * step), step);
			return ba;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Seri.toString64(n0, sb);
			Seri.toString64(n1, sb);
			Seri.toString64(n2, sb);
			Seri.toString64(n3, sb);
			return sb.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (n0 ^ (n0 >>> 32));
			result = prime * result + (int) (n1 ^ (n1 >>> 32));
			result = prime * result + (int) (n2 ^ (n2 >>> 32));
			result = prime * result + (int) (n3 ^ (n3 >>> 32));
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
			TheSha256 other = (TheSha256) obj;
			if (n0 != other.n0)
				return false;
			if (n1 != other.n1)
				return false;
			if (n2 != other.n2)
				return false;
			if (n3 != other.n3)
				return false;
			return true;
		}

	}

	static class Seri {

		public static void toByteArray64(long n, byte[] ba, int off, int len) {
			int end = off + len;
			for (int i = end - 1; i >= off; i--) {
				ba[i] = (byte) (n & 0x0ff);
				n >>= 8;
			}
		}

		public static void toByteArray32(int n, byte[] ba, int off, int len) {
			int end = off + len;
			for (int i = end - 1; i >= off; i--) {
				ba[i] = (byte) (n & 0x0ff);
				n >>= 8;
			}
		}

		public static void toString64(long n, StringBuilder sb) {
			for (int i = 64; i > 0;) {
				i -= 4;
				int n2 = (int) (n >> i);
				char ch = toChar(n2);
				sb.append(ch);
			}
		}

		private final static char[] char_array = "0123456789abcdef"
				.toCharArray();

		private static char toChar(int num) {
			return char_array[num & 0x0f];
		}

		public static void toString32(int n, StringBuilder sb) {
			for (int i = 32; i > 0;) {
				i -= 4;
				int n2 = (int) (n >> i);
				char ch = toChar(n2);
				sb.append(ch);
			}
		}

	}

	static class Parser {

		public static int parse32(byte[] b, int off, int len) {
			int n = 0;
			final int end = off + len;
			for (int i = 0; i < end; ++i) {
				n <<= 8;
				n |= (b[i] & 0x0ff);
			}
			return n;
		}

		public static long parse64(byte[] b, int off, int len) {
			long n = 0;
			final int end = off + len;
			for (int i = 0; i < end; ++i) {
				n <<= 8;
				n |= (b[i] & 0x0ff);
			}
			return n;
		}

		public static long parse64(char[] ch, int off, int len) {
			long n = 0;
			final int end = off + len;
			for (int i = off; i < end; i++) {
				n <<= 4;
				n |= parseChar(ch[i]);
			}
			return n;
		}

		public static int parse32(char[] ch, final int off, final int len) {
			int n = 0;
			final int end = off + len;
			for (int i = off; i < end; i++) {
				n <<= 4;
				n |= parseChar(ch[i]);
			}
			return n;
		}

		private static int parseChar(char ch) {
			if ('0' <= ch && ch <= '9') {
				return (ch - '0');
			} else if ('a' <= ch && ch <= 'f') {
				return (ch - 'a') + 10;
			} else if ('A' <= ch && ch <= 'F') {
				return (ch - 'A') + 10;
			} else {
				return 0;
			}
		}

	}

}
