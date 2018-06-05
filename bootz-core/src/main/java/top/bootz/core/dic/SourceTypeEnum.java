package com.orion.common.dic;

/**
 * 请求方的标识
 * 
 * @author John
 *
 */
public enum SourceTypeEnum {

	Desktop(101), Android(102), IOS(103), HTML5(104);

	private int code;

	private SourceTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public static SourceTypeEnum getSourceTypeByCode(int code) {
		for (SourceTypeEnum sourceType : SourceTypeEnum.values()) {
			if (sourceType.getCode() == code) {
				return sourceType;
			}
		}
		return null;
	}

}
