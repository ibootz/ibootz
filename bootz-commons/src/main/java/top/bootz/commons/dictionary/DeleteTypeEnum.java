package top.bootz.commons.dictionary;

/**
 * 删除状态
 * @author John
 *
 */
public enum DeleteTypeEnum {

	NOT_DELETE(0), DELETED(1);

	private int code;

	private DeleteTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public static DeleteTypeEnum getSourceTypeByCode(int code) {
		for (DeleteTypeEnum sourceType : DeleteTypeEnum.values()) {
			if (sourceType.getCode() == code) {
				return sourceType;
			}
		}
		return null;
	}
	
}
