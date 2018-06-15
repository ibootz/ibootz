package top.bootz.core.converter;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.LockStatusEnum;

/**
 * Desc: 激活枚举与数据库字典映射
 * 
 * @author John
 * 2018年6月10日 下午7:31:28 <br/>
 */
public class LockStatusAttributeConverter implements AttributeConverter<LockStatusEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(LockStatusEnum arg0) {
		return arg0 == null ? LockStatusEnum.NOT_LOCKED.getCode() : arg0.getCode();
	}

	@Override
	public LockStatusEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? LockStatusEnum.NOT_LOCKED : LockStatusEnum.getSourceTypeByCode(arg0);
	}

}
