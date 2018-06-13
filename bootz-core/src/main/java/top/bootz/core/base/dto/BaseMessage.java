package top.bootz.core.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

@Getter
@Setter
public class BaseMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;

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

	public BaseMessage(String type) {
		this(type, null, null, null);
	}

	public BaseMessage(String type, String code) {
		this(type, code, null, null);
	}

	public BaseMessage(String type, String code, String message) {
		this(type, code, message, null);
	}

	public BaseMessage(String type, String code, String message, Throwable throwable) {
		this.setCode(code);
		this.setType(type);
		this.setMessage(message);
		this.throwable = throwable;
	}

}
