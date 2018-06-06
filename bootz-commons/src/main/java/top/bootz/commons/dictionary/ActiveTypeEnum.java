package top.bootz.commons.dictionary;

/**
 * 激活状态
 * 
 * @author John
 *
 */
public enum ActiveTypeEnum {

	NOT_ACTIVE(0), ACTIVED(1);

	private int code;

	private ActiveTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public static ActiveTypeEnum getSourceTypeByCode(int code) {
		for (ActiveTypeEnum sourceType : ActiveTypeEnum.values()) {
			if (sourceType.getCode() == code) {
				return sourceType;
			}
		}
		return null;
	}

}
