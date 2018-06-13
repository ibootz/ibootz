package top.bootz.core.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** Http状态码 */
	private int httpStatus;

	/** 异常类型(WARNNING, ERROR) */
	private String type;

	/** 应用内部自定义错误代码 */
	private String code;

	/** 错误信息 */
	private String message;

	/** 附加说明 */
	private String moreInfo;

	@JsonIgnore
	private Throwable throwable;

	public BaseMessage() {
	}

	public BaseMessage(int httpStatus) {
		this(httpStatus, null, null, null, null);
	}

	public BaseMessage(int httpStatus, String type) {
		this(httpStatus, type, null, null, null);
	}

	public BaseMessage(int httpStatus, String type, String code) {
		this(httpStatus, type, code, null, null);
	}

	public BaseMessage(int httpStatus, String type, String code, String message) {
		this(httpStatus, type, code, message, null);
	}

	public BaseMessage(int httpStatus, String type, String code, String message, Throwable throwable) {
		this.setCode(code);
		this.setHttpStatus(httpStatus);
		this.setType(type);
		this.setMessage(message);
		this.throwable = throwable;
	}

}
