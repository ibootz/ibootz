package top.bootz.commons.exception;

public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(Throwable e) {
		super(e);
	}

	public BaseRuntimeException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public BaseRuntimeException(String errMsg) {
		super(errMsg);
	}

}
