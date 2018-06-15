package top.bootz.core.converter;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.DeleteTypeEnum;

/**
 * Desc: 删除枚举与数据库字典映射
 * 
 * @author John
 * 2018年6月10日 下午7:31:28 <br/>
 */
public class DeleteTypeAttributeConverter implements AttributeConverter<DeleteTypeEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(DeleteTypeEnum arg0) {
		return arg0 == null ? DeleteTypeEnum.NOT_DELETE.getCode() : arg0.getCode();
	}

	@Override
	public DeleteTypeEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? DeleteTypeEnum.NOT_DELETE : DeleteTypeEnum.getSourceTypeByCode(arg0);
	}

}
