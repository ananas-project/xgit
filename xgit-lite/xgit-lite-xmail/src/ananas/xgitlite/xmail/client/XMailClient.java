package ananas.xgitlite.xmail.client;

import java.io.File;

import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.remote.RemoteRepo;
import ananas.xgitlite.xmail.XCommit;

public interface XMailClient {

	LocalRepo getLocalRepo();

	RemoteRepo getRemoteRepo();

	XCommit commit(File path, boolean _R);

	void push(XCommit commit);

}
