package top.bootz.core.base.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户API层面错误消息的传递实体
 * 
 * @author John
 *
 */
@Getter
@Setter
public class RestMessage extends BaseMessage {

	private static final long serialVersionUID = 2911460490542945388L;

	private int httpStatus;

	public RestMessage() {
	}

	public RestMessage(int httpStatus) {
		this(httpStatus, null, null, null, null);
	}

	public RestMessage(int httpStatus, String type) {
		this(httpStatus, type, null, null, null);
	}

	public RestMessage(int httpStatus, String type, String code) {
		this(httpStatus, type, code, null, null);
	}

	public RestMessage(int httpStatus, String type, String code, String message) {
		this(httpStatus, type, code, message, null);
	}

	public RestMessage(int httpStatus, String type, String code, String message, Throwable throwable) {
		super(type, code, message, throwable);
		this.setHttpStatus(httpStatus);
	}

}
