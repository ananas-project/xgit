package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.OutputStream;

import ananas.lib.io.vfs.VFile;

public class LargeLocalObjectBuilder extends AbstractLocalObjectBuilder {

	public LargeLocalObjectBuilder(VFile dir, String type, long length) {
		super(length);
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveTo(VFile path) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected OutputStream openDataOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
