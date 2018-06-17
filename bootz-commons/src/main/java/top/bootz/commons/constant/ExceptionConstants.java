package top.bootz.commons.constant;

public final class ExceptionConstants {

	private ExceptionConstants() {
	}

	/** 400 - BAD_REQUEST */
	public static final String BAD_REQUEST = "global.bad_request";

	/** 400 - api自定义抛出的异常 */
	public static final String API_EXCEPTION = "global.api_exception";

	/** 400 - 类型不匹配异常 */
	public static final String TYPE_MISMATCH_EXCEPTION = "global.type_mismatch_exception";

	/** 401 - Unauthorized Exception */
	public static final String UNAUTHORIZED_EXCEPTION = "global.unauthorized";

	/** 403 - Forbidden Exception */
	public static final String FORBIDDEN_EXCEPTION = "global.permission_denied";

	/** 405 - Method Not Allowed */
	public static final String METHOD_NOT_ALLOWED = "global.method_not_allowed";

	/** 415 - Unsupported Media Type */
	public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED = "global.http_media_type_not_supported";

	/** 500 - Internal Server Error */
	public static final String INTERNAL_SERVER_ERROR = "global.internal_server_error";

	/** 500 - Unknown Exception */
	public static final String UNKNOWN_EXCEPTION = "global.unknown_exception";

	/** 500 - App Exception */
	public static final String APPLICATION_EXCEPTION = "global.application_exception";

	/** 500 - 全局异常处理程序抛出的异常 */
	public static final String RESOLVE_EXCEPTION = "global.resolver.exception";

	/** 500 - 空指针异常 */
	public static final String NULL_POINTER_EXCEPTION = "global.null_pointer_exception";

	/** 500 - 类型转换异常 */
	public static final String CLASS_CAST_EXCEPTION = "global.class_cast_exception";

	/** 500 - IO异常 */
	public static final String IO_EXCEPTION = "global.io_exception";

	/** 500 - 没有找到相应方法异常 */
	public static final String NO_SUCH_METHOD_EXCEPTION = "global.no_such_method_exception";

	/** 500 - 数组越界异常 */
	public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION = "global.index_out_of_bounds_exception";

	/** 406 - 不接受的媒体类型*/
	public static final String HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION = "global.http_media_type_not_acceptable_exception";

}
