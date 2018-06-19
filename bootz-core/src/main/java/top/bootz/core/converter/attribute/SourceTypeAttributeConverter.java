package top.bootz.core.converter.attribute;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.SourceTypeEnum;

/**
 * Desc: 字典类型枚举类与数据库值转换类
 * 
 * @author John
 * 2018年6月10日 下午7:31:28 <br/>
 */
public class SourceTypeAttributeConverter implements AttributeConverter<SourceTypeEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SourceTypeEnum arg0) {
		return arg0 == null ? SourceTypeEnum.UNKNOW.getCode() : arg0.getCode();
	}

	@Override
	public SourceTypeEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? SourceTypeEnum.UNKNOW : SourceTypeEnum.getSourceTypeByCode(arg0);
	}

}
