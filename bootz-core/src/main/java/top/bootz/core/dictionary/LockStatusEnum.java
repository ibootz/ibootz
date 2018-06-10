package top.bootz.core.dictionary;

/**
 * Desc: 锁定状态枚举
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午7:51:30 <br/>
 */
public enum LockStatusEnum {

	NOT_LOCKED(0), LOCKED(1);

	private Integer code;

	private LockStatusEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public static LockStatusEnum getSourceTypeByCode(Integer code) {
		for (LockStatusEnum sourceType : LockStatusEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return LockStatusEnum.NOT_LOCKED;
	}

}
