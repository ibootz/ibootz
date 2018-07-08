package top.bootz.commons.constant;

import java.util.regex.Pattern;

/**
 * 格式化相关的常用模式
 * 
 * @author John
 *
 */
public final class PatternConstants {

    private PatternConstants() {
    }

    public static final Pattern DEFAULT_TIME_PATTERN = Pattern.compile(
            "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$");

    public static final Pattern DEFAULT_UUID_PATTERN = Pattern
            .compile("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_PATTERN_2 = "yyyyMMdd HHmmss";

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_PATTERN_3 = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_PATTERN_4 = "yyyyMMdd";

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_FORMAT_PATTERN_5 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_PATTERN_6 = "yyyyMMddHHmmss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_PATTERN_7 = "yyyyMMddHH";

    /**
     * yyyy-MM-ddTHH:mm:ss.SSS
     */
    public static final String DATE_FORMAT_PATTERN_8 = "yyyy-MM-dd'T'HH:mm:ss.SSS";

}
