package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.exception.DateValideteException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lei
 */
public class JodaTimeUtil {

    private static Logger logger = LoggerFactory.getLogger(JodaTimeUtil.class);
    /**
     * 一天的毫秒数
     */
    public static final long MILLION_SECONDS_OF_DAY = 86400000;
    /**
     * 一小时的数
     */
    public static final long MILLION_SECONDS_OF_HOUR = 3600000;
    /**
     * 一分钟的毫秒数
     */
    public static final long MILLION_SECONDS_OF_MINUTE = 60000;
    /**
     * 日期格式化格式：yyyyMMdd
     */
    public static final String FORMAT_DATE_STR_NO_SEP = "yyyyMMdd";
    /**
     * 日期格式化格式：yyyy-MM-dd
     */
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";
    /**
     * 时间格式化格式：yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_STR_ONE = "yyyy-MM-dd HH:mm";
    /**
     * 时间格式化格式：显示秒
     */
    public static final String FORMAT_DATE_STR_SECOND = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年月日时分秒毫秒(无下划线) yyyyMMddHHmmssSSS
     */
    public static final String FORMAT_DATE_STR_LONG = "yyyyMMddHHmmssSSS";
    /**
     * 年月日时分秒,以横线分隔，类似于文件命名形式
     */
    public static final String FORMAT_DATE_STR_SECOND_FILE_NAME = "yyyy-MM-dd-HH-mm-ss";

    public static final DateTimeFormatter simple_str = DateTimeFormat.forPattern(FORMAT_DATE_STR);

    private static final String HTTP_HEADER_TIME_ZONE = "GMT";
    private static final String HTTP_HEADER_DATE_FORMAT = "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'z";

    /**
     * 获取当前时间
     *
     * @return timestamp
     */
    public static Date getCurrentDate() {
        DateTime dateTime = new DateTime();
        Timestamp timestamp = new Timestamp(dateTime.getMillis());
        return timestamp;
    }

    /**
     * 获取当前时间
     *
     * @return timestamp
     */
    public static Timestamp getCurrentTime() {
        DateTime dateTime = new DateTime();
        Timestamp timestamp = new Timestamp(dateTime.getMillis());
        return timestamp;
    }

    /**
     * 获取 1970年1月1日00:00:00 GMT开始到现在所表示的毫秒数
     *
     * @return
     */
    public static Long getCurrentMillis() {
        DateTime dateTime = new DateTime();
        return dateTime.getMillis();
    }

    /**
     * @param date 格式"yyyy-MM-dd"
     * @return
     */
    public static Date parse(String date) {
        return DateTime.parse(date).toDate();
    }

    public static Date parse(String date, String pattern) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(date, dtf).toDate();
    }

    public static Timestamp parseTimestamp(String date) {
        Date datetime = DateTime.parse(date).toDate();
        Timestamp timestamp = new Timestamp(datetime.getTime());
        return timestamp;
    }

    /**
     * 默认的格式
     *
     * @param date
     * @return 返回默认格式"yyyy-MM-dd"的字符串
     */
    public static String format(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(simple_str);
    }

    /**
     * 将多种格式转化为 yyyy-mm-dd 的字符串
     *
     * @param str
     * @return
     */
    public static String format(String str) {
        Date date;
        try {
            date = DateUtils.parseDate(str, FORMAT_DATE_STR, FORMAT_DATE_STR_ONE, FORMAT_DATE_STR_SECOND, FORMAT_DATE_STR_LONG);
        } catch (ParseException e) {
            throw new DateValideteException("时间格式转换异常");
        }
        String format = format(date);
        return format;
    }

    /**
     * 默认的格式
     *
     * @param dateTime
     * @return 返回默认格式"yyyy-MM-dd"的字符串
     */
    public static String format(DateTime dateTime) {
        return dateTime.toString(simple_str);
    }

    /**
     * @param date   当前时间
     * @param format 格式
     * @return
     */
    public static String format(Date date, String format) {
        DateTime dateTime = new DateTime(date);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
        return dateTime.toString(dtf);
    }

    /**
     * 增加天数
     *
     * @param date yyyy-MM-dd"的字符串
     * @param i
     * @return yyyy-MM-dd"的字符串
     */
    public static String addDay(String date, int i) {
        if (i == 0)
            return date;
        DateTime dateTime = DateTime.parse(date);
        return dateTime.plusDays(i).toString(simple_str);
    }

    /**
     * 增加天数
     *
     * @param date
     * @param i
     * @param format 格式
     * @return
     */
    public static String addDay(String date, int i, String format) {
        if (i == 0)
            return date;
        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
        DateTime dateTime = DateTime.parse(date);
        return dateTime.plusDays(i).toString(dtf);
    }

    /**
     * 得到两个日期之间相差的天数,两头不算,取出数据后，可以根据需要再加
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferDay(Date date1, Date date2) {
        String d1 = format(date1);
        String d2 = format(date2);
        return getDifferDay(d1, d2);
    }

    /**
     * 得到两个日期之间相差的天数,两头不算,取出数据后，可以根据需要再加
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferDay(String date1, String date2) {
        DateTime dateTime1_tmp = DateTime.parse(date1);
        DateTime dateTime2_tmp = DateTime.parse(date2);
        Period p = new Period(dateTime1_tmp, dateTime2_tmp, PeriodType.days());
        int days = p.getDays();
        return Math.abs(days);
    }

    /**
     * 通过开始日期和结束日期，返回这期间内日期的集合
     *
     * @param date1
     * @param date2
     * @return
     */
    public static List<String> getAllDaysByDifferDay(String date1, String date2) throws DateValideteException {
        List<String> result = new ArrayList<>();
        if (!isDate(date1)) {
            throw new DateValideteException(date1);
        }
        if (!isDate(date2)) {
            throw new DateValideteException(date2);
        }
        if (!JodaTimeUtil.compareDate2(date1, date2)) {
            throw new DateValideteException(date1, date2);
        }
        int differs = getDifferDay(date1, date2);
        for (int i = 0; i <= differs; i++) {
            result.add(addDay(date1, i));
        }
        return result;
    }


    /**
     * 校验日期格式，yyyy-MM-dd或者yyyy/MM/dd
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        if (!StringUtils.isEmpty(date)) {
            String newDate = date.replaceAll("/", "-");
            try {
                Date re = JodaTimeUtil.parse(newDate);
                if (re != null) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("不是有效的日期格式："+date);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断当前天是星期几
     *
     * @param date
     * @return 数字
     */
    public static int dayOfWeek(String date) {
        DateTime dateTime = DateTime.parse(date);
        return dateTime.dayOfWeek().get();
    }

    /**
     * 获取一个时间段的星期数
     *
     * @param from
     * @param to
     * @return
     */
    public static List<String> getWeeksByTime(String from, String to) {
        List<String> weeks = new ArrayList<>();
        int differDay = getDifferDay(from, to) + 1;
        for (int i = 0; i < differDay; i++) {
            String now = addDay(from, i);
            String week = String.valueOf(dayOfWeek(now));
            if (!weeks.contains(week)) {
                weeks.add(week);
            }
        }
        return weeks;
    }

    /**
     * 比较时间大小
     *
     * @param date1
     * @param date2
     * @return date1 < date2 true
     */
    public static boolean compareDate(String date1, String date2) {
        DateTime dateTime1 = new DateTime(date1);
        long a = dateTime1.getMillis();
        DateTime dateTime2 = new DateTime(date2);
        long b = dateTime2.getMillis();
        return a < b ? true : false;
    }

    /**
     * 比较时间大小
     *
     * @param date1
     * @param date2
     * @return date1 <= date2 true
     */
    public static boolean compareDate2(String date1, String date2) {
        DateTime dateTime1 = new DateTime(date1);
        long a = dateTime1.getMillis();
        DateTime dateTime2 = new DateTime(date2);
        long b = dateTime2.getMillis();
        return a <= b ? true : false;
    }

    /**
     * 比较时间大小
     *
     * @param date1
     * @param date2
     * @return date1 > date2 返回1
     * date1 < date2 返回-1
     * date1 = date2 返回0
     */
    public static int compareDates(String date1, String date2) {
        DateTime dateTime1 = new DateTime(date1);
        long a = dateTime1.getMillis();
        DateTime dateTime2 = new DateTime(date2);
        long b = dateTime2.getMillis();
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 比较两个时间， 取较大的时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String compareOfMax(String date1, String date2) {
        DateTime dateTime1 = new DateTime(date1);
        long a = dateTime1.getMillis();
        DateTime dateTime2 = new DateTime(date2);
        long b = dateTime2.getMillis();
        return a >= b ? date1 : date2;
    }

    /**
     * 比较两个时间，取较小的时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String compareOfMin(String date1, String date2) {
        DateTime dateTime1 = new DateTime(date1);
        long a = dateTime1.getMillis();
        DateTime dateTime2 = new DateTime(date2);
        long b = dateTime2.getMillis();
        return a >= b ? date2 : date1;
    }

    /**
     * @param date eg: 2015-05-06 -> 2015-05-06 23:59:59
     */
    public static String add23Hours(String date) {
        if (StringUtils.isNotEmpty(date)) {
            String day = addDay(date, 1);
            DateTime dateTime = DateTime.parse(day);
            DateTime time = dateTime.plusSeconds(-1);
            return time.toString(FORMAT_DATE_STR_SECOND);

        }
        return null;
    }

    /**
     * 判断两个时间是否属于同一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean judgeOfOneMonth(String date1, String date2) {
        DateTime dt1 = DateTime.parse(date1);
        DateTime dt2 = DateTime.parse(date2);
        return dt1.year().get() == dt2.year().get() && dt1.monthOfYear().get() == dt2.monthOfYear().get();
    }

    /**
     * 获取本月第一天时间类型
     *
     * @param date
     * @return
     */
    public static Timestamp getFirstDayOfMontht(String date) {
        DateTime dt = DateTime.parse(date);
        DateTime dateTime = dt.dayOfMonth().withMinimumValue();
        return parseTimestamp(format(dateTime));
    }

    /**
     * 获取本月第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDayOfMonth(String date) {
        DateTime dt = DateTime.parse(date);
        DateTime dateTime = dt.dayOfMonth().withMinimumValue();
        return format(dateTime);
    }

    /**
     * 获取本月最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(String date) {
        DateTime dt = DateTime.parse(date);
        DateTime dateTime = dt.dayOfMonth().withMaximumValue();
        return format(dateTime);
    }

    /**
     * 采用美国国家标准时间格式，返回值为当前系统时间。例如：Mon, 12 Oct 2015 07:09:23 GMT
     *
     * @return
     */
    public static String getCurrentUSGMT() {
        Date sysdate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(HTTP_HEADER_DATE_FORMAT, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone(HTTP_HEADER_TIME_ZONE));
        String date = df.format(sysdate);
        return date;
    }

    public static void testDif() {
        try {
            System.out.println(getDifferDay("2015-11-10", "2015-11-14"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test1() {
        System.out.println(parse("2015-01-01"));
        System.out.println(parse("2015-01-01 12:00:00", FORMAT_DATE_STR_SECOND));
    }

    public static void test2() {
        System.out.println(format(new Date(), FORMAT_DATE_STR_SECOND));
    }

    public static void test3() {
        DateTime dateTime = new DateTime(2015, 2, 28, 1, 1, 1);
        System.out.println(format(dateTime));
    }

    public static void test4() {
        System.out.println(addDay("2015-2-28", 0));
    }

    public static void test5() {
        System.out.println(compareOfMax("2013-1-1", "2011-2-2"));
        System.out.println(compareOfMin("2015-1-1", "2015-1-1"));
    }

    public static void test6() {
        System.out.println(parseTimestamp("2015-11-1"));
    }

    public static void main(String[] args) {
        List<String> weeksByTime = getWeeksByTime("2016-05-26", "2016-05-31");
        System.out.println();
    }

    private static void testDaa() {
        Date date = null;
        try {
            date = DateUtils.parseDate("2016-04-30 00:00:00", FORMAT_DATE_STR, FORMAT_DATE_STR_ONE, FORMAT_DATE_STR_SECOND, FORMAT_DATE_STR_LONG);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String format = format(date);
        System.out.println(format);
    }
}
