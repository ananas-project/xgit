package ananas.xgit;

public class XGitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6886701625264674320L;

	public XGitException() {
	}

	public XGitException(String msg) {
		super(msg);
	}

	public XGitException(Exception e) {
		super(e);
	}

}
