package ananas.xgitlite;

public abstract class XGitLite implements XGitLiteAPI {

	private static XGitLite _xgl;

	public static XGitLite getInstance() {
		XGitLite ret = _xgl;
		if (ret == null) {
			ret = XGitLiteFactory.getXGitLite();
			_xgl = ret;
		}
		return ret;
	}

}
