package top.bootz.core.converter.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public enum DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    INSTANCE;

    @Override
    public LocalDateTime convert(Date source) {
        return source == null ? null : LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }

}
