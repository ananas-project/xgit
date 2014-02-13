package ananas.xgitlite.xmail.client;

import java.io.File;
import java.net.URI;

public interface XMailClientFactory {

	/**
	 * @param remote
	 *            the URI of remote control endpoint.
	 * @param local
	 *            the path of local repository.
	 * */

	XMailClient createXMailClient(URI remote, File local);

}
