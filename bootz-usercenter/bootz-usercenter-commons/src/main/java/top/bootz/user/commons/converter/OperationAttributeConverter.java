package top.bootz.user.commons.converter;

import javax.persistence.AttributeConverter;

import com.google.common.base.Preconditions;

import top.bootz.user.commons.dictionary.OperationEnum;

/**
 * Desc: 权限类型枚举类与数据库值转换类
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午7:31:28 <br/>
 */
public class OperationAttributeConverter implements AttributeConverter<OperationEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(OperationEnum arg0) {
		Preconditions.checkArgument(arg0 != null, "Illegal authorityEnum");
		return arg0.getCode();
	}

	@Override
	public OperationEnum convertToEntityAttribute(Integer arg0) {
		Preconditions.checkArgument(arg0 != null, "Illegal authorityEnum code.");
		return OperationEnum.getSourceTypeByCode(arg0);
	}

}
