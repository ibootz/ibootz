package top.bootz.commons.exception;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String CHECK_ARGUMENT_MESSAGE = "errorKey must not be blank!";

    private final String errorKey;

    private final String[] args;

    /**
     * @param httpStatus Http状态码
     * @param errorKey   国际化消息key
     * @param args       国际化消息value格式化所需要的参数
     * @param errMsg     异常message
     * @param cause      异常堆栈
     */
    public ApiException(String errorKey, String[] args, String defaultMessage, Throwable cause) {
        super(StringUtils.isBlank(defaultMessage) ? errorKey : defaultMessage, cause);
        Preconditions.checkArgument(StringUtils.isNotBlank(errorKey), CHECK_ARGUMENT_MESSAGE);
        this.errorKey = errorKey;
        this.args = args;
    }

    /**
     * @param httpStatus Http状态码
     * @param errorKey   国际化消息key
     * @param errMsg     异常message
     */
    public ApiException(String errorKey, String defaultMessage) {
        this(errorKey, new String[0], defaultMessage, null);
        Preconditions.checkArgument(StringUtils.isNotBlank(errorKey), CHECK_ARGUMENT_MESSAGE);
    }

    /**
     * @param httpStatus Http状态码
     * @param errorKey   国际化消息key
     * @param args       国际化消息value格式化所需要的参数
     */
    public ApiException(String errorKey, String[] args) {
        this(errorKey, args, errorKey, null);
        Preconditions.checkArgument(StringUtils.isNotBlank(errorKey), CHECK_ARGUMENT_MESSAGE);
    }

    /**
     * @param httpStatus Http状态码
     * @param errorKey   国际化消息key
     */
    public ApiException(String errorKey) {
        this(errorKey, null, errorKey, null);
        Preconditions.checkArgument(StringUtils.isNotBlank(errorKey), CHECK_ARGUMENT_MESSAGE);
    }

}
