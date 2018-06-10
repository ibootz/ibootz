package top.bootz.core.dictionary;

/**
 * 硬件设备类型
 * 
 * @author John
 *
 */
public enum DeviceEnum {

	UNKNOWN(0, "Unknown"), PC(1, "PC"), MOBILE(2, "Mobile"), TABLET(3, "Tablet");

	private String name;

	private Integer code;

	private DeviceEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static DeviceEnum getSourceTypeByCode(Integer code) {
		for (DeviceEnum sourceType : DeviceEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return DeviceEnum.UNKNOWN;
	}

}
