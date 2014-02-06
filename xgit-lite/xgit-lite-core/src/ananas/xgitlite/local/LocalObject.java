package ananas.xgitlite.local;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgitlite.XGLObject;

public interface LocalObject extends XGLObject {

	InputStream openPlainInputStream() throws IOException;

	InputStream openZipInputStream() throws IOException;

	OutputStream openZipOutputStream();

	boolean exists();

}
