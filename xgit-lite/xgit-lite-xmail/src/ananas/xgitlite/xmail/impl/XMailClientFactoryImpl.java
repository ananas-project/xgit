package ananas.xgitlite.xmail.impl;

import java.io.File;
import java.net.URI;

import ananas.xgitlite.xmail.client.XMailClient;
import ananas.xgitlite.xmail.client.XMailClientFactory;

class XMailClientFactoryImpl implements XMailClientFactory {

	private final static XMailClientFactoryImpl _default;

	static {
		_default = new XMailClientFactoryImpl();
	}

	public static XMailClientFactory getDefault() {
		return _default;
	}

	@Override
	public XMailClient createXMailClient(URI remote, File local) {
		return new XMailClientImpl(remote, local);
	}

}
