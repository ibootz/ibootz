package top.bootz.core.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 消息类型
 *
 * @author John <br/>
 * @time 2018年6月17日 下午9:28:44
 */
public enum MessageStatusEnum {

    SUCCESS("Success"),

    WARNING("Warning"),

    ERROR("Error");

    private final String desc;

    MessageStatusEnum(String desc) {
        this.desc = desc;
    }

    @JsonValue
    public String getDesc() {
        return this.desc;
    }

    public static MessageStatusEnum getMessageStatusByDesc(String desc) {
        for (MessageStatusEnum type : values()) {
            if (type.desc.equalsIgnoreCase(desc)) {
                return type;
            }
        }
        return MessageStatusEnum.WARNING;
    }

}
