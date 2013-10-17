package ananas.xgit.repo.local;

import java.io.InputStream;
import java.io.OutputStream;

import ananas.xgit.repo.XGitObject;

public interface LocalObject extends XGitObject {

	LocalObjectBank getBank();

	InputStream openRawInputStream();

	OutputStream openRawOutputStream();

}
