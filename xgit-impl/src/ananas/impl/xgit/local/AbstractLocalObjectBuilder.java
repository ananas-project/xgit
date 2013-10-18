package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.OutputStream;

import ananas.xgit.repo.ObjectId;

public abstract class AbstractLocalObjectBuilder extends OutputStream implements
		LocalObjectBuilder {

	private final long _reg_length;
	private final HashObjectIdOutputStream _id_out;
	private OutputStream _data_out;
	private long _cb = 0;

	public AbstractLocalObjectBuilder(long length) {
		_reg_length = length;
		_id_out = new HashObjectIdOutputStream();
	}

	@Override
	public OutputStream openOutput(byte[] head) throws IOException {
		_data_out = this.openDataOutput();
		_id_out.write(head);
		_data_out.write(head);
		return this;
	}

	/**
	 * the plain output stream
	 * */
	protected abstract OutputStream openDataOutput();

	@Override
	public ObjectId build() {
		return _id_out.genId();
	}

	@Override
	public void write(int b) throws IOException {
		_id_out.write(b);
		_data_out.write(b);
		_cb++;
	}

	@Override
	public void write(byte[] buffer, int offset, int length) throws IOException {
		_id_out.write(buffer, offset, length);
		_data_out.write(buffer, offset, length);
		_cb += length;
	}

	@Override
	public void close() throws IOException {
		_id_out.close();
		_data_out.close();
		if (_cb != _reg_length) {
			throw new IOException("the file size not match (want/real) :"
					+ _reg_length + "/" + _cb);
		}
	}

}
