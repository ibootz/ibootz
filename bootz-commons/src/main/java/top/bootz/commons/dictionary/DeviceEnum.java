package top.bootz.commons.dictionary;

/**
 * 硬件设备类型
 * 
 * @author John
 *
 */
public enum DeviceEnum {

	UNKNOWN(0, "Unknown"), PC(1, "PC"), MOBILE(2, "Mobile"), TABLET(3, "Tablet");

	private String name;

	private int code;

	private DeviceEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static DeviceEnum getSourceTypeByCode(int code) {
		for (DeviceEnum sourceType : DeviceEnum.values()) {
			if (sourceType.getCode() == code) {
				return sourceType;
			}
		}
		return null;
	}

}
