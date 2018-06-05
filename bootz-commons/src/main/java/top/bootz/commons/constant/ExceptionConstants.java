package top.bootz.commons.constant;

public final class ExceptionConstants {

	private ExceptionConstants(){
	}
	
	/**
	 * 500 - Internal Server Error
	 */
	public static final String INTERNAL_SERVER_ERROR = "global.internal_server_error";
	
	/**
	 * 500 - Unknown Exception
	 */
	public static final String UNKNOWN_EXCEPTION = "global.unknown_exception";
	
	/**
	 * 500 - App Exception
	 */
	public static final String APPLICATION_EXCEPTION = "global.application_exception";
	
	/**
	 * 415 - Unsupported Media Type
	 */
	public static final String CONTENT_TYPE_NOT_SUPPORTED = "global.content_type_not_supported";
	
	/**
	 * 405 - Method Not Allowed
	 */
	public static final String METHOD_NOT_ALLOWED = "global.method_not_allowed";
	
	/**
	 * 400 - BAD_REQUEST
	 */
	public static final String BAD_REQUEST = "global.bad_request";
	
	/**
	 * 400 - App Exception
	 */
	public static final String API_EXCEPTION = "global.api_exception";
	
	/**
	 * 401 - Unauthorized Exception
	 */
	public static final String UNAUTHORIZED_EXCEPTION = "global.unauthorized";
	
	/**
	 * 403 - Forbidden Exception
	 */
	public static final String FORBIDDEN_EXCEPTION = "global.permission_denied";

	/**
	 * 全局异常处理程序抛出的异常
	 */
	public static final String RESOLVE_EXCEPTION = "global.resolver.exception";
	
}
