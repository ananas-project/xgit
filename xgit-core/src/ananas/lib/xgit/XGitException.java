package ananas.lib.xgit;

public class XGitException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XGitException(String msg) {
		super(msg);
	}

	public XGitException(Throwable throwable) {
		super(throwable);
	}

}
