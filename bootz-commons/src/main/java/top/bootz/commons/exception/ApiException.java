package top.bootz.commons.exception;

public class ApiException extends BaseRuntimeException {

	private static final long serialVersionUID = -2650776598316245642L;

	private final String errorKey;

	private final String[] args;

	/**
	 * 
	 * @param errorKey
	 *            国际化消息key
	 * @param args
	 *            国际化消息value格式化所需要的参数
	 * @param errMsg
	 *            异常message
	 * @param cause
	 *            异常堆栈
	 */
	public ApiException(String errorKey, String[] args, String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errorKey = errorKey;
		this.args = args;
	}

	/**
	 * 
	 * @param errorKey
	 *            国际化消息key
	 * @param errMsg
	 *            异常message
	 * @param cause
	 *            异常堆栈
	 */
	public ApiException(String errorKey, String errMsg, Throwable cause) {
		this(errorKey, new String[0], errMsg, cause);
	}

	/**
	 * 
	 * @param errorKey
	 *            国际化消息key
	 * @param args
	 *            国际化消息value格式化所需要的参数
	 * @param errMsg
	 *            异常message
	 */
	public ApiException(String errorKey, String[] args, String errMsg) {
		this(errorKey, args, errMsg, null);
	}

	/**
	 * 
	 * @param errorKey
	 *            国际化消息key
	 * @param args
	 *            国际化消息value格式化所需要的参数
	 */
	public ApiException(String errorKey, String[] args) {
		super();
		this.errorKey = errorKey;
		this.args = args;
	}

	public ApiException(String errorKey) {
		this(errorKey, new String[0]);
	}

	public String getErrorKey() {
		return errorKey;
	}

	public Object[] getArgs() {
		return args;
	}

}
