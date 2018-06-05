package top.bootz.commons.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public enum DateToLocalDateConverter implements Converter<Date, LocalDate> {

	INSTANCE;

	@Override
	public LocalDate convert(Date source) {
		return source == null ? null
				: LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault()).toLocalDate();
	}

}
