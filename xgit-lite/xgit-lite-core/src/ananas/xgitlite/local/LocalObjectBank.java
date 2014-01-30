package ananas.xgitlite.local;

import java.io.File;
import java.io.InputStream;

import ananas.xgitlite.ObjectId;

public interface LocalObjectBank {

	LocalObject getObject(ObjectId id);

	LocalObject addObject(String type, long length, InputStream in);

	LocalObject addObject(String type, File file);

}
