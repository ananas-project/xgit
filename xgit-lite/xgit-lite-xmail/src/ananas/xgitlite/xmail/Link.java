package ananas.xgitlite.xmail;

import java.io.File;

import ananas.xgitlite.ObjectId;

public interface Link {

	String name();

	long size();

	File local_file();

	String type();

	ObjectId id();

}
