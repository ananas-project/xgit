package ananas.lib.xgit;

public class DefaultRepositoryProfile implements RepositoryProfile {

	public String repoDirName = ".git";

	@Override
	public String getRepositoryDirectoryName() {
		return this.repoDirName;
	}

}
