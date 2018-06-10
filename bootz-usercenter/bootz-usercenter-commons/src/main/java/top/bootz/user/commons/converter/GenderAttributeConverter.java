package top.bootz.user.commons.converter;

import javax.persistence.AttributeConverter;

import top.bootz.user.commons.dictionary.GenderEnum;

public class GenderAttributeConverter implements AttributeConverter<GenderEnum, String> {

	@Override
	public String convertToDatabaseColumn(GenderEnum attribute) {
		return attribute.name();
	}

	@Override
	public GenderEnum convertToEntityAttribute(String dbData) {
		return GenderEnum.valueOf(dbData);
	}

}
