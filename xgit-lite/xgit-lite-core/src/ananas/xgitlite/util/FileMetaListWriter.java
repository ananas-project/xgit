package ananas.xgitlite.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FileMetaListWriter {

	private final Writer _writer;
	private boolean _started;

	public FileMetaListWriter(OutputStream out) {
		this._writer = new OutputStreamWriter(out);
		this._started = false;
	}

	public void write(FileMeta meta) throws IOException {

		final char sp = ',';
		final StringBuilder sb = new StringBuilder();
		final List<String> list = new ArrayList<String>();

		if (!this._started) {
			this._started = true;

			list.add(FileMeta.Const.field_list);
			list.add(FileMeta.Field.id);
			list.add(FileMeta.Field.last_mod);
			list.add(FileMeta.Field.length);
			list.add(FileMeta.Field.mode);
			list.add(FileMeta.Field.path);
			list.add(FileMeta.Field.type);

			for (String s : list) {
				if (sb.length() > 0) {
					sb.append(sp);
				}
				sb.append(s);
			}
			sb.append(FileMeta.Const.crlf);
			_writer.append(sb);

			list.clear();
			sb.setLength(0);
		}

		list.add(FileMeta.Const.file);
		list.add(Formatter.toString(meta.id));
		list.add(Formatter.toString(meta.last_modified));
		list.add(Formatter.toString(meta.length));
		list.add(Formatter.toString(meta.mode));
		list.add(Formatter.toString(meta.path));
		list.add(Formatter.toString(meta.type));

		for (String s : list) {
			if (sb.length() > 0) {
				sb.append(sp);
			}
			sb.append(s);
		}
		sb.append(FileMeta.Const.crlf);
		_writer.append(sb);
	}

	static class Formatter {

		public static String toString(String s) {
			if (s == null)
				return "null";
			return s;
		}

		public static String toString(long n) {
			return Long.toString(n);
		}
	}

	public void flush() throws IOException {
		this._writer.flush();
	}

	public void close() throws IOException {
		this._writer.flush();
		this._writer.close();
	}
}
