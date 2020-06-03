package com.jacky.launcher.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    private static final long INTERVAL = 60;

    private static final SimpleDateFormat SDF1;
    private static final SimpleDateFormat SDF2;
    private static final SimpleDateFormat SDF3;
    private static final SimpleDateFormat SDF4;
    private static final SimpleDateFormat SDF5;

    static {
        SDF1 = new SimpleDateFormat("aa hh:mm");
        SDF2 = new SimpleDateFormat("昨天aa hh:mm");
        SDF3 = new SimpleDateFormat("M月d日aa hh:mm");
        SDF4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SDF5 = new SimpleDateFormat("yyyyMMdd");
    }

    public static long StringToLong(String s) {
        Date date = new Date();
        try {
            date = SDF4.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String formatDate(String s) {
        long currentTime = System.currentTimeMillis() / 1000;
        long time = StringToLong(s) / 1000;
        if (isCloseEnough(time, currentTime)) {
            return "刚刚";
        } else if (Math.abs(time - currentTime) < 60 * 60) {
            return Math.abs(currentTime - time) / 60 + "分钟前";
        } else if (Math.abs(time - currentTime) < 24 * 60 * 60) {
            return Math.abs(currentTime - time) / (60 * 60) + "小时前";
        } else if (Math.abs(time - currentTime) < 24 * 60 * 60 * 30) {
            return Math.abs(currentTime - time) / (60 * 60 * 24) + "天前";
        } else {
            return s;
        }
    }

    public static long DataToLong(Date date) {
        long lSysTime1 = date.getTime();   //得到秒数，Date类型的getTime()返回毫秒数
        return lSysTime1;
    }

    public static String LongToString(long time) {
        Date dt = new Date(time);
        String sDateTime = SDF5.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    public static String getTimestampString(long time) {
        if (isSameDay(time)) {
            return SDF1.format(new Date(time));
        } else if (isYesterday(time)) {
            return SDF2.format(new Date(time));
        } else {
            return SDF3.format(new Date(time));
        }
    }

    private static boolean isSameDay(long inputTime) {
        TimeInfo tStartAndEndTime = getTodayStartAndEndTime();
        if (inputTime > tStartAndEndTime.getStartTime() && inputTime < tStartAndEndTime.getEndTime())
            return true;
        return false;
    }


    private static boolean isYesterday(long inputTime) {
        TimeInfo yStartAndEndTime = getYesterdayStartAndEndTime();
        if (inputTime > yStartAndEndTime.getStartTime() && inputTime < yStartAndEndTime.getEndTime())
            return true;
        return false;
    }


    /**
     * 获取今天00:00:00~23:59:59 开始和结束的时间戳
     */
    private static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * 获取昨天00:00:00~23:59:59 开始和结束的时间戳
     */
    private static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * 判断两个时间戳是否小于 INTERVAL
     */
    public static boolean isCloseEnough(long time1, long time2) {
        return Math.abs(time2 - time1) < INTERVAL;
    }


    public static class TimeInfo {
        private long startTime;
        private long endTime;

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }

    public static String getTimestampString(Date messageDate) {
        String format = null;
        String language = Locale.getDefault().getLanguage();
        boolean isZh = language.startsWith("zh");
        long messageTime = messageDate.getTime();
        if (isSameDay(messageTime)) {
            if(isZh) {
                format = "aa hh:mm";
            } else {
                format = "hh:mm aa";
            }
        } else if (isYesterday(messageTime)) {
            if(isZh){
                format = "昨天aa hh:mm";
            }else{
                return "Yesterday " + new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(messageDate);
            }
        } else {
            if(isZh){
                format = "M月d日aa hh:mm";
            }
            else{
                format = "MMM dd hh:mm aa";
            }
        }
        if(isZh){
            return new SimpleDateFormat(format,Locale.CHINESE).format(messageDate);
        }else{
            return new SimpleDateFormat(format,Locale.ENGLISH).format(messageDate);
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        @SuppressLint("UseValueOf") long lt = new Long(s);
        Date date = new Date(lt * 1000);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间转换为时间戳，另一种格式
     */
    public static String dateToDay(String s) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("UseValueOf") long lt = new Long(s);
        Date date = new Date(lt * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 返回某月第一天是周几
     */
    public static int getMonthFirstDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取某年某月有多少天
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}