package top.bootz.user.commons.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 操作类型枚举
 * 
 * @author John
 *
 */
public enum OperationEnum {

	NONE(0), VIEW(1), ADD(2), MODIFY(3), DELETE(4), EXECUTE(5), UPLOAD(6), DOWNLOAD(7);

	private Integer code;

	private OperationEnum(Integer code) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public static OperationEnum getSourceTypeByCode(Integer code) {
		for (OperationEnum sourceType : OperationEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return OperationEnum.NONE;
	}

}
