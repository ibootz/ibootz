package top.bootz.core.converter;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.DeviceEnum;

/**
 * Desc: 激活枚举与数据库字典映射
 * 
 * @author John
 * 2018年6月10日 下午7:31:28 <br/>
 */
public class DeviceTypeAttributeConverter implements AttributeConverter<DeviceEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(DeviceEnum arg0) {
		return arg0 == null ? DeviceEnum.UNKNOWN.getCode() : arg0.getCode();
	}

	@Override
	public DeviceEnum convertToEntityAttribute(Integer arg0) {
		return arg0 == null ? DeviceEnum.UNKNOWN : DeviceEnum.getSourceTypeByCode(arg0);
	}

}
