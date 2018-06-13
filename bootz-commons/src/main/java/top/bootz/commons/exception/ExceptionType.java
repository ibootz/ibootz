package top.bootz.commons.exception;

public enum ExceptionType {

	WARNING("WARNING"),

	ERROR("ERROR");

	private final String desc;

	private ExceptionType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public static ExceptionType getExceptionTypeBy(String desc) {
		for (ExceptionType type : values()) {
			if (type.desc.equalsIgnoreCase(desc)) {
				return type;
			}
		}
		return ExceptionType.WARNING;
	}

}
