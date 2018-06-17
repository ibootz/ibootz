package top.bootz.user.commons.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 权限类型枚举
 * 
 * @author John
 *
 */
public enum ResourceEnum {

	NONE(0, "None"), MENU(1, "Menu"), ELEMENT(2, "Element"), FILE(3, "File"), URL(5, "Url");

	private Integer code;

	private String desc;

	private ResourceEnum(Integer code, String desc) {
		this.code = code;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	public static ResourceEnum getSourceTypeByCode(Integer code) {
		for (ResourceEnum sourceType : ResourceEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return ResourceEnum.NONE;
	}

}
