package top.bootz.user.commons.converter;

import javax.persistence.AttributeConverter;

import com.google.common.base.Preconditions;

import top.bootz.user.commons.dictionary.ResourceEnum;

/**
 * Desc: 权限类型枚举类与数据库值转换类
 * 
 * @author John 2018年6月10日 下午7:31:28 <br/>
 */
public class ResourceAttributeConverter implements AttributeConverter<ResourceEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ResourceEnum arg0) {
		Preconditions.checkArgument(arg0 != null, "Illegal resourceEnum");
		return arg0.getCode();
	}

	@Override
	public ResourceEnum convertToEntityAttribute(Integer arg0) {
		Preconditions.checkArgument(arg0 != null, "Illegal resourceEnum code.");
		return ResourceEnum.getSourceTypeByCode(arg0);
	}

}
