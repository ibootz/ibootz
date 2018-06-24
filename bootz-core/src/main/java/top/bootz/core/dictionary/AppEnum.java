package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 系统内各微应用的名称和编号
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午8:18:13
 */
public enum AppEnum {

	UNKNOW(0, "未知"),

	USER_CENTER(5, "用户中心"),

	SECURITY(10, "安全中心"),

	ORDER(15, "商城");

	private int code;

	private String desc;

	AppEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static GenderEnum getGenderByCode(String code) {
		for (GenderEnum genderEnum : GenderEnum.values()) {
			if (genderEnum.getCode().equalsIgnoreCase(code)) {
				return genderEnum;
			}
		}
		return GenderEnum.UNKNOW;
	}

	public static GenderEnum getGenderByDesc(String desc) {
		for (GenderEnum genderEnum : GenderEnum.values()) {
			if (genderEnum.getDesc().equalsIgnoreCase(desc)) {
				return genderEnum;
			}
		}
		return GenderEnum.UNKNOW;
	}

	@JsonValue
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return this.desc;
	}

}
