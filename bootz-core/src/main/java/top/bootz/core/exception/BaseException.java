package top.bootz.core.exception;

public class BaseException extends Exception {

	private static final long serialVersionUID = 7715882058448022167L;

	public BaseException() {
		super();
	}

	public BaseException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public BaseException(String errMsg) {
		super(errMsg);
	}

}
