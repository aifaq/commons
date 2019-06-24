package me.aifaq.commons.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:10 2017/5/17
 */
public abstract class DateUtil {
    public static final String yyyy = "yyyy";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHH = "yyyyMMddHH";
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmssS = "yyyyMMddHHmmssS";
    public static final String yyyyMMddHHmmssSS = "yyyyMMddHHmmssSS";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyyy$MM = "yyyy-MM";
    public static final String yyyy$MM$dd = "yyyy-MM-dd";
    public static final String yyyy$MM$dd$HH = "yyyy-MM-dd HH";
    public static final String yyyy$MM$dd$HH$mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy$MM$dd$HH$mm$ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy$MM$dd$HH$mm$ss$S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String yyyy$MM$dd$HH$mm$ss$SS = "yyyy-MM-dd HH:mm:ss.SS";
    public static final String yyyy$MM$dd$HH$mm$ss$SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    // 小时数
    // 一天
    public static final long ONE_DAY_HOURS = 24L;
    // 一周
    public static final long ONE_WEEK_HOURS = 7 * ONE_DAY_HOURS;

    // 分钟数
    // 一小时
    public static final long ONE_HOUR_MINUTES = 60L;
    // 一天
    public static final long ONE_DAY_MINUTES = 24 * ONE_HOUR_MINUTES;
    // 一周
    public static final long ONE_WEEK_MINUTES = 7 * ONE_DAY_MINUTES;

    // 秒数
    // 一分
    public static final long ONE_MINUTE_SECONDS = 60L;
    // 一小时
    public static final long ONE_HOUR_SECONDS = 60 * ONE_MINUTE_SECONDS;
    // 一天
    public static final long ONE_DAY_SECONDS = 24 * ONE_HOUR_SECONDS;
    // 一周
    public static final long ONE_WEEK_SECONDS = 7 * ONE_DAY_SECONDS;

    // 毫秒数
    // 一秒
    public static final long ONE_SECOND_MILLIS = 1000L;
    // 一分
    public static final long ONE_MINUTE_MILLIS = 60 * ONE_SECOND_MILLIS;
    // 一小时
    public static final long ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS;
    // 一天
    public static final long ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS;
    // 一周
    public static final long ONE_WEEK_MILLIS = 7 * ONE_DAY_MILLIS;

    /**
     * @see #parseDate(String)
     */
    public static Date parse(String source) {
        try {
            return parseDate(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException(source + " 这是日期？");
        }
    }

    /**
     * 尽可能的把字符串转成日期
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String source) throws ParseException {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();

        switch (source.length()) {
            case 4:
                return DateUtils.parseDate(source, yyyy);
            case 6:
                return DateUtils.parseDate(source, yyyyMM);
            case 7:
                return DateUtils.parseDate(source, yyyy$MM);
            case 8:
                return DateUtils.parseDate(source, yyyyMMdd);
            case 10:
                return DateUtils.parseDate(source, yyyy$MM$dd, yyyyMMddHH);
            case 12:
                return DateUtils.parseDate(source, yyyyMMddHHmm);
            case 13:
                // 1494511806790
                if (NumberUtils.isCreatable(source)) {
                    return new Date(Long.parseLong(source));
                }
                return DateUtils.parseDate(source, yyyy$MM$dd$HH);
            case 14:
                return DateUtils.parseDate(source, yyyyMMddHHmmss);
            case 15:
                return DateUtils.parseDate(source, yyyyMMddHHmmssS);
            case 16:
                return DateUtils.parseDate(source, yyyy$MM$dd$HH$mm, yyyyMMddHHmmssSS);
            case 17:
                return DateUtils.parseDate(source, yyyyMMddHHmmssSSS);
            case 19:
                return DateUtils.parseDate(source, yyyy$MM$dd$HH$mm$ss);
            case 21:
                return DateUtils.parseDate(source, yyyy$MM$dd$HH$mm$ss$S);
            case 22:
                return DateUtils.parseDate(source, yyyy$MM$dd$HH$mm$ss$SS);
            case 23:
                return DateUtils.parseDate(source, yyyy$MM$dd$HH$mm$ss$SSS);
            default:
                throw new ParseException("Unable to parse the date: " + source, -1);
        }
    }

    public static Date newDate() {
        return new Date();
    }

    public static Timestamp newTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取最小的日期，忽略null值
     *
     * @param dates
     * @param <T>
     * @return
     */
    public static <T extends Date> T minIgnoreNull(T... dates) {
        if (ArrayUtils.isEmpty(dates)) {
            return null;
        }
        T min = null;
        for (T date : dates) {
            if (date == null) {
                continue;
            }
            if (min == null || date.getTime() < min.getTime()) {
                min = date;
            }
        }
        return min;
    }

    /**
     * 获取最小的日期，如果存在null值则返回null
     *
     * @param dates
     * @param <T>
     * @return
     */
    public static <T extends Date> T minPriorNull(T... dates) {
        if (ArrayUtils.isEmpty(dates)) {
            return null;
        }
        T min = null;
        for (T date : dates) {
            if (date == null) {
                return null;
            }
            if (min == null || date.getTime() < min.getTime()) {
                min = date;
            }
        }
        return min;
    }

    /**
     * 获取最大的日期，忽略null值
     *
     * @param dates
     * @param <T>
     * @return
     */
    public static <T extends Date> T maxIgnoreNull(T... dates) {
        if (ArrayUtils.isEmpty(dates)) {
            return null;
        }
        T max = null;
        for (T date : dates) {
            if (date == null) {
                continue;
            }
            if (max == null || date.getTime() > max.getTime()) {
                max = date;
            }
        }
        return max;
    }

    /**
     * 获取最大的日期，如果存在null值则返回null
     *
     * @param dates
     * @param <T>
     * @return
     */
    public static <T extends Date> T maxPriorNull(T... dates) {
        if (ArrayUtils.isEmpty(dates)) {
            return null;
        }
        T max = null;
        for (T date : dates) {
            if (date == null) {
                return null;
            }
            if (max == null || date.getTime() > max.getTime()) {
                max = date;
            }
        }
        return max;
    }
}
