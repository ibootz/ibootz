package top.bootz.core.converter.attribute;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.DictionaryTypeEnum;

/**
 * Desc: 字典类型枚举类与数据库值转换类
 * 
 * @author John 2018年6月10日 下午7:31:28 <br/>
 */
public class DictionaryTypeAttributeConverter implements AttributeConverter<DictionaryTypeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DictionaryTypeEnum arg0) {
        return arg0 == null ? DictionaryTypeEnum.NONE.getCode() : arg0.getCode();
    }

    @Override
    public DictionaryTypeEnum convertToEntityAttribute(Integer arg0) {
        return arg0 == null ? DictionaryTypeEnum.NONE : DictionaryTypeEnum.getSourceTypeByCode(arg0);
    }

}
