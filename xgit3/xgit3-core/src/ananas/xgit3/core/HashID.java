package ananas.xgit3.core;

public interface HashID {

	byte[] toByteArray();

	class Factory {

		public static HashID create(String s) {
			return ThisModule.getModule().newHashID(s);
		}

		public static HashID create(byte[] b) {
			return ThisModule.getModule().newHashID(b);
		}

	}

}
