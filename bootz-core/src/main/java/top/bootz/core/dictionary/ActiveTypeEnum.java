package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 激活状态枚举
 * 
 * @author John
 *
 */
public enum ActiveTypeEnum {

	NOT_ACTIVE(0), ACTIVED(1);

	private Integer code;

	private ActiveTypeEnum(Integer code) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public static ActiveTypeEnum getSourceTypeByCode(Integer code) {
		for (ActiveTypeEnum sourceType : ActiveTypeEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return ActiveTypeEnum.NOT_ACTIVE;
	}

}
