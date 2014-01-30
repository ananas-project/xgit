package ananas.xgitlite.local;

import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgitlite.XGLObject;

public interface LocalObject extends XGLObject {

	InputStream openPlainInputStream();

	InputStream openZipInputStream();

	OutputStream openZipOutputStream();

	boolean exists();

}
