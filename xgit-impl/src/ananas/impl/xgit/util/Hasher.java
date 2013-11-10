package ananas.impl.xgit.util;

import java.security.MessageDigest;

public class Hasher {

	public static String stringBySHA1(String s) {
		try {
			MessageDigest sha1 = java.security.MessageDigest
					.getInstance("SHA-1");

			byte[] ba = sha1.digest(s.getBytes("UTF-8"));

			return toString(ba);

		} catch (Exception e) {
			e.printStackTrace();
			// .... 0123456789012345678901234567890123456789
			return "0000000000000000000000000000000000000000";
		}
	}

	private final static char[] hexChar = "0123456789abcdef".toCharArray();

	public static String toString(byte[] ba) {
		StringBuilder sb = new StringBuilder();
		for (byte b : ba) {
			sb.append(hexChar[(b >> 4) & 0x0f]);
			sb.append(hexChar[(b) & 0x0f]);
		}
		return sb.toString();
	}

}
