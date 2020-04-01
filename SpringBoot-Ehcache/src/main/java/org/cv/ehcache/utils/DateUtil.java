package org.cv.ehcache.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Created by Ale on 2020/4/1
 */
public class DateUtil {

    /**
     * 获取当前时间 (默认中国时间)
     * @return
     */
    public static LocalDateTime getDateTime() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.systemDefault());
        return dateTime;
    }

    /**
     * 获得不同时区的时间
     *
     * @param zone
     * @return
     */
    public static LocalDateTime getZoneDateTime(String zone) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(zone));
        return dateTime;
    }

    /**
     * 当前日期--date
     *
     * @return
     */
    public static LocalDate getDate() {
        return LocalDate.now();
    }

    /**
     * 当前时间--time
     *
     * @return
     */
    public static LocalTime geTime() {
        return LocalTime.now();
    }

    /**
     * 当前时间转换成 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatterDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(getDateTime());
    }

    /**
     * 时间格式化
     * @param dateTime
     * @return
     */
    public static String formatterDate(LocalDateTime dateTime,String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(dateTime);
    }

    /**
     * 当前时间转long
     *
     * @return
     */
    public static long toLong() {
        // 当前时间,默认时区
        return getDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间转long
     *
     * @param dateTime
     * @return
     */
    public static long toLong(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * long 转 date
     * @return
     */
    public static LocalDateTime toDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 传入long日期 格式化
     * @param timestamp
     * @param format
     * @return
     */
    public static String toDateTime(long timestamp,String format) {
        LocalDateTime dateTime =  LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return formatterDate(dateTime, format);
    }

    /**
     * 字符串转 时间
     * @param dateTime
     * @return
     */
    public static LocalDateTime toDateTime(String dateTime) throws Throwable{
        if(isMatch(dateTime)) {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        throw new Exception("this format is wrong >>> yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 当前年份
     *
     * @return
     */
    public static int getYear() {
        return getDate().getYear();
    }

    public static int getYear(LocalDateTime dateTime) {
        return dateTime.getYear();
    }
    public static int getYear(String dateTime) throws Throwable {
        return toDateTime(dateTime).getYear();
    }

    public static int getYear(LocalDate date) {
        return date.getYear();
    }


    /**
     * 当前月份
     *
     * @return
     */
    public static int getMonth() {
        return getDate().getMonthValue();
    }

    public static int getMonth(LocalDateTime dateTime) {
        return dateTime.getMonthValue();
    }

    public static int getMonth(LocalDate date) {
        return date.getMonthValue();
    }

    /**
     * 当前日
     *
     * @return
     */
    public static int getDay() {
        return getDate().getDayOfMonth();
    }

    public static int getDay(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    public static int getDay(LocalDate date) {
        return date.getDayOfMonth();
    }


    /**
     * 校检传入时间格式是否正确
     * @param val
     * @return
     */
    public static boolean isMatch(String val) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(val).matches();
    }
}
