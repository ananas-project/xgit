package ananas.impl.xgit.local;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.lib.io.vfs.VFileOutputStream;
import ananas.lib.io.vfs.VFileSystem;

public class SmallLocalObjectBuilder extends AbstractLocalObjectBuilder {

	private final ByteArrayOutputStream _baos = new ByteArrayOutputStream();

	public SmallLocalObjectBuilder(VFile dir, String type, long length) {
		super(length);
	}

	@Override
	public void delete() {
		// do nothing
	}

	@Override
	public void moveTo(VFile path) throws IOException {

		VFileSystem vfs = path.getVFS();
		VFile dir = path.getParentFile();
		String name = path.getName();
		VFile tmp = vfs.newFile(dir, name + "~.tmp");

		ByteArrayOutputStream zipBuf = new ByteArrayOutputStream();
		OutputStream out2zip = new DeflaterOutputStream(zipBuf);
		out2zip.write(_baos.toByteArray());
		out2zip.close();

		VFileOutputStream out = new VFileOutputStream(tmp);
		out.write(zipBuf.toByteArray());
		out.close();

		tmp.renameTo(path);

	}

	@Override
	protected OutputStream openDataOutput() {
		return _baos;
	}


}
