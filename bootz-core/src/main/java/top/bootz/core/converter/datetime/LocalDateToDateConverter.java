package top.bootz.core.converter.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public enum LocalDateToDateConverter implements Converter<LocalDate, Date> {

	INSTANCE;

	@Override
	public Date convert(LocalDate source) {
		return source == null ? null : Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
