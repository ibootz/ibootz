package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 删除状态枚举
 * 
 * @author John
 *
 */
public enum DeleteTypeEnum {

	NOT_DELETE(0), DELETED(1);

	private Integer code;

	private DeleteTypeEnum(Integer code) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public static DeleteTypeEnum getSourceTypeByCode(Integer code) {
		for (DeleteTypeEnum sourceType : DeleteTypeEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return DeleteTypeEnum.NOT_DELETE;
	}

}
