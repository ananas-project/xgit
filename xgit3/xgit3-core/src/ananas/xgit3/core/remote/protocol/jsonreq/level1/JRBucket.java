package ananas.xgit3.core.remote.protocol.jsonreq.level1;

import java.util.List;

import ananas.xgit3.core.remote.RemoteRepo;

public interface JRBucket {

	interface GetInfo {

		String method_name = "getInfo";

		interface Request {

			String class_ = "class";
			String id = "id";
			String address = "address";

		}

		interface Response {

			String id = "id";
			String address_list = "address_list";
			String name = "name";
			String control_endpoint = "control_point";
			String data_endpoint = "data_point";

		}

		void setId(String id);

		void setAddress(String addr);

		boolean execute();

		List<String> getAddressList();

		String getName();

		String getControlEndpointURI();

		String getDataEndpointURI();

		String getBucketId();

	}

	String class_name = "Bucket";

	GetInfo getInfo();

	class Factory {

		public static JRBucket newInstance(RemoteRepo repo) {
			return new DefaultJRBucket(repo);
		}

	}

}
