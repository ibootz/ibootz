package top.bootz.core.dictionary;

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

	private MessageStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public static MessageStatusEnum getExceptionTypeBy(String desc) {
		for (MessageStatusEnum type : values()) {
			if (type.desc.equalsIgnoreCase(desc)) {
				return type;
			}
		}
		return MessageStatusEnum.WARNING;
	}

}
