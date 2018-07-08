package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 请求方的标识
 * 
 * @author John
 *
 */
public enum SourceTypeEnum {

    UNKNOW(0), DESKTOP(101), ANDROID(102), IOS(103), HTML5(104);

    private Integer code;

    private SourceTypeEnum(Integer code) {
        this.code = code;
    }

    @JsonValue
    public Integer getCode() {
        return this.code;
    }

    public static SourceTypeEnum getSourceTypeByCode(Integer code) {
        for (SourceTypeEnum sourceType : SourceTypeEnum.values()) {
            if (sourceType.getCode().equals(code)) {
                return sourceType;
            }
        }
        return SourceTypeEnum.UNKNOW;
    }

}
