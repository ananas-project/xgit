package ananas.impl.xgit.local;

import java.io.IOException;
import java.io.OutputStream;

import ananas.lib.io.vfs.VFile;
import ananas.xgit.repo.ObjectId;

public interface LocalObjectBuilder {

	OutputStream openOutput(byte[] head) throws IOException;

	ObjectId build();

	void delete();

	void moveTo(VFile path) throws IOException;

}
