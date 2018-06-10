package top.bootz.core.converter;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.ActiveTypeEnum;

/**
 * Desc: 激活枚举与数据库字典映射
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午7:31:28 <br/>
 */
public class ActiveTypeAttributeConverter implements AttributeConverter<ActiveTypeEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ActiveTypeEnum arg0) {
		return arg0 == null ? ActiveTypeEnum.NOT_ACTIVE.getCode() : arg0.getCode();
	}

	@Override
	public ActiveTypeEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? ActiveTypeEnum.NOT_ACTIVE : ActiveTypeEnum.getSourceTypeByCode(arg0);
	}

}
