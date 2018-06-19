package top.bootz.core.converter.attribute;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * Desc: 禁用枚举与数据库字典映射
 * 
 * @author John
 * 2018年6月10日 下午7:31:28 <br/>
 */
public class DisableTypeAttributeConverter implements AttributeConverter<DisableTypeEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(DisableTypeEnum arg0) {
		return arg0 == null ? DisableTypeEnum.ENABLE.getCode() : arg0.getCode();
	}

	@Override
	public DisableTypeEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? DisableTypeEnum.ENABLE : DisableTypeEnum.getSourceTypeByCode(arg0);
	}

}
