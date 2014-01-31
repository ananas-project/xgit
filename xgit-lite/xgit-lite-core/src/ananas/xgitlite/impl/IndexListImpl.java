package ananas.xgitlite.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import ananas.xgitlite.local.IndexInfo;
import ananas.xgitlite.local.IndexList;
import ananas.xgitlite.local.IndexListReader;
import ananas.xgitlite.local.IndexListWriter;

public class IndexListImpl implements IndexList {

	private final File _file;

	public IndexListImpl(File file) {
		this._file = file;
	}

	@Override
	public IndexListWriter openWriter() throws FileNotFoundException {
		return new MyWriter(this._file);
	}

	@Override
	public IndexListReader openReader() {
		return new MyReader(this._file);
	}

	class MyWriter implements IndexListWriter {

		private final Writer _wtr;
		private final OutputStream _out;
		private final File _file_tmp;
		private final File _file_final;

		public MyWriter(File file) throws FileNotFoundException {

			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			_file_tmp = new File(file.getParentFile(), file.getName() + "~tmp");
			_file_final = file;
			_out = new FileOutputStream(_file_tmp);
			_wtr = new OutputStreamWriter(_out);
		}

		@Override
		public void write(IndexInfo info) throws IOException {

			char sp = ',';
			String end = "\r\n";

			StringBuilder sb = new StringBuilder();

			sb.append(info.last_mod);
			sb.append(sp);
			sb.append(info.id);
			sb.append(sp);
			sb.append(info.path);
			sb.append(sp);
			sb.append(info.length);
			sb.append(end);

			_wtr.write(sb.toString());
		}

		@Override
		public void close() throws IOException {
			_wtr.flush();
			_out.close();
			if (_file_final.exists()) {
				_file_final.delete();
			}
			_file_tmp.renameTo(_file_final);
			System.out.println("index to " + _file_final);
		}
	}

	class MyReader implements IndexListReader {

		public MyReader(File _file) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public IndexInfo read() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}
	}

}
