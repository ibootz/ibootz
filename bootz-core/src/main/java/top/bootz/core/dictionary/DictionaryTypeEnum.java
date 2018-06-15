package top.bootz.core.dictionary;

/**
 * Desc: 字典类型枚举
 * 
 * @author John
 * 2018年6月10日 下午7:07:56 <br/>
 */
public enum DictionaryTypeEnum {

	NONE(0), COUNTRY(1), PROVINCE(2), CITY(3);

	private Integer code;

	private DictionaryTypeEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public static DictionaryTypeEnum getSourceTypeByCode(Integer code) {
		for (DictionaryTypeEnum sourceType : DictionaryTypeEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return DictionaryTypeEnum.NONE;
	}

}
