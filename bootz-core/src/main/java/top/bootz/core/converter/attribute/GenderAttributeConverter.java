package top.bootz.core.converter.attribute;

import javax.persistence.AttributeConverter;

import top.bootz.core.dictionary.GenderEnum;

/**
 * 实体类中GenderEnum类型和数据库中保存数据类型之间的转换器
 * 
 * @author John
 *
 */
public class GenderAttributeConverter implements AttributeConverter<GenderEnum, String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        return GenderEnum.getGenderByCode(dbData);
    }

}
