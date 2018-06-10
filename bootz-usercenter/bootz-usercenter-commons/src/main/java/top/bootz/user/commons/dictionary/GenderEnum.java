package top.bootz.user.commons.dictionary;

public enum GenderEnum {

	Male("男性", "m"), Female("女性", "f"), Others("其他", "o"), Unknow("未知", "u");

	private String desc;

	private String code;

	private GenderEnum(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	public GenderEnum getGenderByDesc(String desc) {
		for (GenderEnum genderEnum : GenderEnum.values()) {
			if (genderEnum.getDesc().equalsIgnoreCase(desc)) {
				return genderEnum;
			}
		}
		return null;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getCode() {
		return code;
	}

}
