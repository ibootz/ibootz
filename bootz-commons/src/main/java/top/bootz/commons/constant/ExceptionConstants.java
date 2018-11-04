package top.bootz.commons.constant;

public final class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final class ErrorMessageKey {

        private ErrorMessageKey() {
        }

        /** 400 - BAD_REQUEST */
        public static final String BAD_REQUEST = "global.bad_request";

        /** 400 - api自定义抛出的异常 */
        public static final String API_EXCEPTION = "global.api_exception";

        /** 400 - 类型不匹配异常 */
        public static final String TYPE_MISMATCH_EXCEPTION = "global.type_mismatch_exception";

        /** 401 - 跨域异常 */
        public static final String CORS_EXCEPTION = "global.cors_exception";

        /** 401 - 未认证异常 */
        public static final String UNAUTHORIZED_EXCEPTION = "global.unauthorized";

        /** 403 - 未授权异常 */
        public static final String FORBIDDEN_EXCEPTION = "global.forbidden";

        /** 405 - 不被允许的方法 */
        public static final String METHOD_NOT_ALLOWED = "global.method_not_allowed";

        /** 406 - 不接受的媒体类型 */
        public static final String HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION = "global.http_media_type_not_acceptable_exception";

        /** 415 - 不支持的媒体类型 */
        public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED = "global.http_media_type_not_supported";

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

        /** 500 - 数据库访问异常 */
        public static final String DATA_ACCESS_EXCEPTION = "global.data_access_exception";

        /** 500 - 应用自定义异常 */
        public static final String APPLICATION_EXCEPTION = "global.application_exception";

        /** 500 - 服务器内部异常 */
        public static final String INTERNAL_SERVER_ERROR = "global.internal_server_error";

    }

}
