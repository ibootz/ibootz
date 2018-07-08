package top.bootz.core.converter.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public enum LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

    INSTANCE;

    @Override
    public Date convert(LocalDateTime source) {
        return source == null ? null : Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
    }

}
