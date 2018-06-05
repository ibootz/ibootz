package top.bootz.commons.helper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import top.bootz.commons.constant.PatternConstants;

/**
 * 日期处理相关的工具方法
 * 
 * @author John
 *
 */
public final class DateHelper {

	public static final long MILLIS_PER_SECOND = 1000;

	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	public static final long MILLIS_PER_WEEK = 7 * MILLIS_PER_DAY;

	public static final String ERROR_MSG_1 = "The date must not be null";

	public static final String ERROR_MSG_2 = "The parameters must not be null or blank";

	private DateHelper() {

	}

	/**
	 * 日期转换为字符串.
	 * <p>
	 * 默认格式 yyyy-MM-dd HH:mm:ss
	 * </p>
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2str(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return new SimpleDateFormat(PatternConstants.DATE_FORMAT_PATTERN_1).format(date);
	}

	/**
	 * 字符串转换成日期.
	 * <p>
	 * 默认格式：yyyy-MM-dd HH:mm:ss
	 * </p>
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date str2date(String str) {
		if (StringUtils.isBlank(str)) {
			throw new IllegalArgumentException("The parameter must not be null or blank");
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(PatternConstants.DATE_FORMAT_PATTERN_1).parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("日期解析失败 [" + e.getMessage() + "]");
		}
		return date;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2str(Date date, String pattern) {
		if (date == null || StringUtils.isBlank(pattern)) {
			throw new IllegalArgumentException(ERROR_MSG_2);
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date str2date(String str, String pattern) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
			throw new IllegalArgumentException(ERROR_MSG_2);
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("日期解析失败 [" + e.getMessage() + "]");
		}
		return date;
	}

	/**
	 * date转换成Calendar
	 */
	public static Calendar dateToCalendar(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Calendar转换成字符串
	 * 
	 * @param cal
	 * @param pattern
	 * @return
	 */
	public static String calendar2str(Calendar cal, String pattern) {
		if (cal == null || StringUtils.isBlank(pattern)) {
			throw new IllegalArgumentException(ERROR_MSG_2);
		}
		return DateHelper.date2str(cal.getTime(), pattern);
	}

	/**
	 * 字符串转换成Calendar
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Calendar str2Calendar(String str, String pattern) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
			throw new IllegalArgumentException(ERROR_MSG_2);
		}
		Date date = DateHelper.str2date(str, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 获取指定date的年月日数组
	 * 
	 * @param date
	 * @return
	 */
	public static int[] getYmd(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.YEAR) + 1;
		int day = cal.get(Calendar.DATE);
		return new int[] { year, month, day };
	}

	/**
	 * 获取指定date的时分秒数组
	 * 
	 * @param date
	 * @return
	 */
	public static int[] getHms(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		return new int[] { hour, minute, second };
	}

	/**
	 * 获取今年的起点 2017-01-01 00:00:00
	 */
	public static Date getStartOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定年份的起点 2017-01-01 00:00:00
	 */
	public static Date getStartOfYear(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取今年的最后一天终点 2017-12-31 23:59:59
	 */
	public static Date getEndOfYear() {
		Date date = getStartOfYear();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取指定年份最后一天终点 2017-12-31 23:59:59
	 */
	public static Date getEndOfYear(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Date tempDate = getStartOfYear(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tempDate);
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取当月的起点 07-01 00:00:00
	 * 
	 * @param args
	 */
	public static Date getStartOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定月份的起点 07-01 00:00:00
	 * 
	 * @param args
	 */
	public static Date getStartOfMonth(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当月的终点 07-01 23:59:59
	 */
	public static Date getEndOfMonth() {
		Date date = getStartOfMonth();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取指定月份的终点 07-01 23:59:59
	 */
	public static Date getEndOfMonth(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Date tempDate = getStartOfMonth(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tempDate);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取当天的起点 00:00:00
	 * 
	 * @param args
	 */
	public static Date getStartOfDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定天份的起点 00:00:00
	 * 
	 * @param args
	 */
	public static Date getStartOfDay(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当天的终点 23:59:59
	 * 
	 * @param args
	 */
	public static Date getEndOfDay() {
		Date date = getStartOfDay();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取当天的终点 23:59:59
	 * 
	 * @param args
	 */
	public static Date getEndOfDay(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Date tempDate = getStartOfDay(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tempDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取当前小时的起点 06:00:00.000
	 * 
	 * @param args
	 */
	public static Date getStartOfHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定小时的起点 06:00:00.000
	 * 
	 * @param args
	 */
	public static Date getStartOfHour(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前小时的终点 06:59:59.999
	 * 
	 * @param args
	 */
	public static Date getEndOfHour() {
		Date date = getStartOfHour();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获取当前小时的终点 06:59:59.999
	 * 
	 * @param args
	 */
	public static Date getEndOfHour(Date date) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Date tempDate = getStartOfHour(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tempDate);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 增加amount年，传负数则相应减少
	 * 
	 * @param args
	 */
	public static Date addYears(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 增加amount月，传负数则相应减少
	 * 
	 * @param args
	 */
	public static Date addMonths(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 增加amount周，传负数则相应减少
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addWeeks(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * 增加amount日，传负数则相应减少
	 * 
	 * @param args
	 */
	public static Date addDays(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 增加amount时，传负数则相应减少
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 增加amount分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 增加amount秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSeconds(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 增加amount毫秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliseconds(Date date, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return add(date, Calendar.MILLISECOND, amount);
	}

	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, amount);
		return cal.getTime();
	}

	/**
	 * 根据给定的时间单位换算成毫秒值返回
	 * 
	 * @param unit
	 * @return
	 */
	public static long getMillisPerUnit(int unit) {
		long result = Long.MAX_VALUE;
		switch (unit) {
		case Calendar.DAY_OF_YEAR:
		case Calendar.DATE:
			result = MILLIS_PER_DAY;
			break;
		case Calendar.HOUR_OF_DAY:
			result = MILLIS_PER_HOUR;
			break;
		case Calendar.MINUTE:
			result = MILLIS_PER_MINUTE;
			break;
		case Calendar.SECOND:
			result = MILLIS_PER_SECOND;
			break;
		case Calendar.MILLISECOND:
			result = 1;
			break;
		default:
			throw new IllegalArgumentException("The unit " + unit + " cannot be represented is milleseconds");
		}
		return result;
	}

	/**
	 * 判断是否是同一年
	 */
	public static boolean isSameYear(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameYear(cal1, cal2);
	}

	/**
	 * 判断是否是同一年
	 */
	public static boolean isSameYear(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	}

	/**
	 * 判断是否是同一年的同一个月
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameMonth(cal1, cal2);
	}

	/**
	 * 判断是否是同一年的同一个月
	 */
	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	}

	/**
	 * 判断是否是同一年同一月的同一天
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * 判断是否是同一年同一月的同一天
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 判断是否是同一年同一月的同一周
	 */
	public static boolean isSameWeek(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameWeek(cal1, cal2);
	}

	/**
	 * 判断是否是同一年同一月的同一天
	 */
	public static boolean isSameWeek(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR));
	}

	/**
	 * 判断是否是同一年同一月的同一小时
	 */
	public static boolean isSameHour(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameHour(cal1, cal2);
	}

	/**
	 * 判断是否是同一年同一月的同一小时
	 */
	public static boolean isSameHour(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * 判断是否是同一年同一月的同一分钟
	 */
	public static boolean isSameMinute(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameMinute(cal1, cal2);
	}

	/**
	 * 判断是否是同一年同一月的同一分钟
	 */
	public static boolean isSameMinute(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE);
	}

	/**
	 * 比较两个瞬时是否相等
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		return date1.getTime() == date2.getTime();
	}

	/**
	 * 获取两个日期之间相差多少天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		Double diff = Double.valueOf((endDate.getTime() - startDate.getTime()) / (double) MILLIS_PER_DAY);
		return Integer.valueOf((int) diff.doubleValue());
	}

	/**
	 * 获取两个时间之间的相差多少小时
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getHoursBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		Double diff = Double.valueOf((endDate.getTime() - startDate.getTime()) / MILLIS_PER_HOUR);
		return Integer.valueOf((int) diff.doubleValue());
	}

	/**
	 * 获取两个时间之间的相差多少分钟
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getMinutesBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		Double diff = Double.valueOf((endDate.getTime() - startDate.getTime()) / MILLIS_PER_MINUTE);
		return Integer.valueOf((int) diff.doubleValue());
	}

	/**
	 * 获取两个时间之间的相差多少秒
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getSecondsBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException(ERROR_MSG_1);
		}
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		Double diff = Double.valueOf((endDate.getTime() - startDate.getTime()) / MILLIS_PER_SECOND);
		return Integer.valueOf((int) diff.doubleValue());
	}

	/**
	 * 今天：当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 昨天：当前时间 - 1
	 * 
	 * @return
	 */
	public static Date yesterday() {
		Date date = new Date();
		date.setTime(now().getTime() - MILLIS_PER_DAY);
		return date;
	}

	/**
	 * 明天：当前时间 + 1天
	 * 
	 * @return
	 */
	public static Date tomorrow() {
		Date date = new Date();
		date.setTime(now().getTime() + MILLIS_PER_DAY);
		return date;
	}

	/**
	 * 获取当前的时间戳
	 * 
	 * @return
	 */
	public static Timestamp timestamp() {
		return new Timestamp(now().getTime());
	}

	/**
	 * 获取指定日期的时间戳
	 */
	public static Timestamp timestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static Calendar calendar() {
		return Calendar.getInstance();
	}

	/**
	 * 计算过期时间到当下这一秒之间间隔的秒数
	 */
	public static long getIntervalSeconds(LocalDateTime startTime, LocalDateTime endTime) {
		if (startTime != null && endTime != null) {
			return endTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()
					- startTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		}
		return 0;
	}

}
