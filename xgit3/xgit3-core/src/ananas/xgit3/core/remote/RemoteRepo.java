package ananas.xgit3.core.remote;

public interface RemoteRepo {

	RemoteObjectBank getBank();

	String getControlEndpointURI();

	class Factory {

		public static RemoteRepo create(RemoteRepoInfo info) {
			return ThisModule.getModule().newRemoteRepo(info);
		}

	}

}
