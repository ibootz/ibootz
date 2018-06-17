package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 禁用状态枚举
 * 
 * @author John
 *
 */
public enum DisableTypeEnum {

	ENABLE(0), DISABLE(1);

	private Integer code;

	private DisableTypeEnum(Integer code) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public static DisableTypeEnum getSourceTypeByCode(Integer code) {
		for (DisableTypeEnum sourceType : DisableTypeEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return DisableTypeEnum.ENABLE;
	}

}
