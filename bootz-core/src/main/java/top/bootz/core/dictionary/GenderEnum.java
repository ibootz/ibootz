package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum {

    UNKNOW("u", "未知"), MALE("m", "男性"), FEMALE("f", "女性"), OTHERS("o", "其他");

    private String code;

    private String desc;

    GenderEnum(String code, String desc) {
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
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return this.desc;
    }

}
