package top.bootz.core.converter.datetime;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * Timestamp转换器 将Date转换成Timestamp
 * 
 * @ClassName: TimestampConverter
 * @author John
 *
 */
public enum DateToTimestampConverter implements Converter<Date, Timestamp> {

    INSTANCE;

    @Override
    public Timestamp convert(Date source) {
        return source == null ? null : new Timestamp(source.getTime());
    }

}
