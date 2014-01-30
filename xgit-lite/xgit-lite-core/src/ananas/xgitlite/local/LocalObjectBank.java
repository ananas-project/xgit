package ananas.xgitlite.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;

public interface LocalObjectBank {

	LocalObject getObject(ObjectId id);

	LocalObject addObject(String type, long length, InputStream in)
			throws IOException, XGLException;

	LocalObject addObject(String type, File file) throws IOException,
			XGLException;

}
