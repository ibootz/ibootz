package top.bootz.core.dictionary;

public enum GenderEnum {

    Male("男性", "m"), Female("女性", "f"), Others("其他", "o"), Unknow("未知", "u");

    private String desc;

    private String code;

    GenderEnum(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public static GenderEnum getGenderByCode(String code) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getCode().equalsIgnoreCase(code)) {
                return genderEnum;
            }
        }
        return GenderEnum.Unknow;
    }

    public static GenderEnum getGenderByDesc(String desc) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getDesc().equalsIgnoreCase(desc)) {
                return genderEnum;
            }
        }
        return GenderEnum.Unknow;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCode() {
        return code;
    }

}
