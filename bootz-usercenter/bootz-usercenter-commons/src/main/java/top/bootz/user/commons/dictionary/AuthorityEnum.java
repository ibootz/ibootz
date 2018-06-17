package top.bootz.user.commons.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 权限类型枚举
 * 
 * @author John
 *
 */
public enum AuthorityEnum {

	NONE(0), MENU(1), ELEMENT(2), OPERATION(3), FILE(4), URL(5);

	private Integer code;

	private AuthorityEnum(Integer code) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public static AuthorityEnum getSourceTypeByCode(Integer code) {
		for (AuthorityEnum sourceType : AuthorityEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return AuthorityEnum.NONE;
	}

}
