package ananas.xgitlite.util;

public class FileMeta {

	interface Const {

		String file = "$FILE";
		String field_list = "$FIELD_LIST";
		String crlf = "\r\n";

	}

	interface Field {

		String id = "ID";
		String last_mod = "LAST_MODIFIED";
		String length = "LENGTH";
		String mode = "MODE";
		String path = "PATH";
		String type = "TYPE";

	}

	public String id;
	public int mode;
	public String path;
	public long last_modified;
	public String type;
	public long length;

}
