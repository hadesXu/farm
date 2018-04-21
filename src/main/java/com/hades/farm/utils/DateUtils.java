package com.hades.farm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhengzl on 2018/3/14.
 */
public class DateUtils {
    public static final long SECOND = 1000;
    public static final long MINUTES = SECOND * 60;
    public static final long HOUR = 60 * MINUTES;
    public static final long DAY = 24 * HOUR;
    public static final long YEAR = 365 * DAY;
    public static ThreadLocal<DateFormat> YYYY_MM_DD_MM_HH_SS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static ThreadLocal<DateFormat> YYYY_MM_DD = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static ThreadLocal<DateFormat> YYYY_MM = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };

    public static ThreadLocal<DateFormat> YYYYMMDDMMHHSSSSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }
    };

    public static ThreadLocal<DateFormat> YYMMDDMMHHSSSSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmssSSS");
        }
    };

    public static ThreadLocal<DateFormat> YYYYMMDDHHMMSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    public static ThreadLocal<DateFormat> YYYYMMDD = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static ThreadLocal<DateFormat> HHMMSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("HHmmss");
        }
    };

    public static ThreadLocal<DateFormat> HHMMSS2 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };


    /**
     * 根据时间获取时间戳
     *
     * @param time
     * @return
     */
    public static long getDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.getTimeInMillis();
    }


    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return YYYY_MM_DD_MM_HH_SS.get().format(date);
    }

    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            date = YYYY_MM_DD_MM_HH_SS.get().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String fmDate(Date date) {
        String datefm = YYYY_MM_DD_MM_HH_SS.get().format(date);
        return datefm.substring(11, 13) + "时" + datefm.substring(14, 16) + "分";
    }

    /**
     * 判断两个时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 判断两个时间
     *
     * @param date1
     * @param date2
     * @return date1 晚于 date2  返回true
     */
    public static boolean isDateBefore2(String date1, String date2) {
        if ("".equals(date1) || "".equals(date2)) {
            return false;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static Date strToYYMMDDDate(String dateString) {
        Date date = null;
        try {
            date = YYYY_MM_DD.get().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToYYMMDDStr(Date d) {
        String s = null;
        if (d != null) {
            s = YYYY_MM_DD.get().format(d);
        }
        return s;
    }


    /**
     * 计算两个时间之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        days = (end - start) / 86400000;
        return days;
    }

    /**
     * 计算两个时间之间相差的小时数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffHours(Date startDate, Date endDate) {
        long hours = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        hours = (end - start) / (1000 * 60 * 60);
        return hours;
    }

    public static long diffMinute(Date startDate, Date endDate) {
        long hours = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        hours = (end - start) / (1000 * 60);
        return hours;
    }

    /**
     * 日期加上月数的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 日期加上天数的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加上年数的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 计算剩余时间 (多少天多少时多少分)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "过期";
        }
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1) {
            result.append("过期");
        } else {
            long temp = 1000 * 60 * 60 * 24;
            // 天数
            long d = times / temp;

            // 小时数
            times %= temp;
            temp /= 24;
            long m = times / temp;
            // 分钟数
            times %= temp;
            temp /= 60;
            long s = times / temp;

            result.append(d);
            result.append("天");
            result.append(m);
            result.append("小时");
            result.append(s);
            result.append("分");
        }
        return result.toString();
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    /**
     * @MethodName: getLinkUrl
     * @Param: DateUtil flag ： true 转换 false 不转换
     * @Author: gang.lv
     * @Date: 2013-5-8 下午02:52:44
     * @Return:
     * @Descb:
     * @Throws:
     */
    public static String getLinkUrl(boolean flag, String content, String id) {
        if (flag) {
            content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
        }
        return content;
    }

    /**
     * 时间转换为时间戳
     *
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeCur(String format, String date)
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 时间转换为时间戳
     *
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeCur(String format, Date date)
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 将时间戳转为字符串
     *
     * @param cc_time
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 时间转换为时间戳
     *
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeCurS(String format, Date date)
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 获取当前周的周一时间
     *
     * @return
     * @throws ParseException
     */
    public static String getWeekTime()
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        return df.format(cal.getTime());
    }

    /**
     * 获取当前周的周日时间
     *
     * @return
     * @throws ParseException
     */
    public static String getMonthTime()
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return df.format(cal.getTime());
    }

    public static String dateToString2(Date date) {
        return YYYYMMDDHHMMSS.get().format(date);
    }

    /**
     * 获取长中短期时间
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String getLongMiddleShortFlag(String startDate, String endDate) throws Exception {
        startDate = startDate.substring(0, 10) + " 00:00:00";
        endDate = endDate.substring(0, 10) + " 00:00:00";
        Date start = YYYY_MM_DD_MM_HH_SS.get().parse(startDate);
        Date end = YYYY_MM_DD_MM_HH_SS.get().parse(endDate);

        long diffDays = DateUtils.diffDays(start, end);

        String flag = "";
        if (diffDays > 90) {
            flag = "l";
        } else if (diffDays > 40 && diffDays <= 90) {
            flag = "m";
        } else if (diffDays <= 40) {
            flag = "s";
        }
        return flag;
    }

    /**
     * 两日期间的天数计算
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Long diffDaysString(String startDate, String endDate) throws Exception {
        startDate = startDate.substring(0, 10) + " 00:00:00";
        endDate = endDate.substring(0, 10) + " 00:00:00";
        Date start = YYYY_MM_DD_MM_HH_SS.get().parse(startDate);
        Date end = YYYY_MM_DD_MM_HH_SS.get().parse(endDate);
        Long diffDays = DateUtils.diffDays(start, end);
        return diffDays;
    }

    public static Calendar getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.SATURDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal;
    }

    public static Calendar getFirstDayOfWeekInfo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.FRIDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal;
    }

    public static Calendar getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.SATURDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        return cal;
    }

    public static Calendar getLastDayOfWeekInfo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.FRIDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        return cal;
    }

    public static Calendar getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal;
    }

    public static Calendar getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);

        return cal;
    }

    public static String getDateStr(Date d, String format) {
        SimpleDateFormat dsf = new SimpleDateFormat(format);
        return dsf.format(d);
    }


    public static Date getStrDate(String s, String format) throws ParseException {
        SimpleDateFormat dsf = new SimpleDateFormat(format);
        return dsf.parse(s);
    }

    public static String getDayStr(Date date) {
        String s = null;
        if (date != null) {
            s = YYYYMMDD.get().format(date);
        }
        return s;
    }

    public static String getDay(Date date) {
        String s = null;
        if (date != null) {
            s = YYYY_MM_DD_MM_HH_SS.get().format(date);
        }
        return s;
    }


    /**
     * 计算n个工作日后的日期
     *
     * @param n
     * @param inDate
     * @return
     */
    public static String workDayOffset(int n, Date inDate) {
        Calendar c1 = Calendar.getInstance();
        int workDay = n;
        c1.setTime(inDate);
        if (workDay > 0) {

            for (int i = 0; i < workDay; i++) {
                // 判断当天是否为周末，如果是周末加1
                if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
                    workDay = workDay + 1;
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                    continue;
                }
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                // 当天数加1 判断是否为周末 如果是周末加1
                if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
                    workDay = workDay + 1;
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                    continue;
                }
            }
        } else {
            for (int i = 0; i > workDay; i--) {
                // 判断当天是否为周末，如果是周末加1
                if (Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
                    workDay = workDay - 1;
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                    continue;
                } else if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY)) {
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                    continue;
                }
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                // 当天数加1 判断是否为周末 如果是周末加1
                if (Calendar.SUNDAY == c1.get(Calendar.SATURDAY)) {
                    workDay = workDay - 1;
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                    continue;
                } else if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY)) {
                    c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
                    continue;
                }
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c1.getTime());
    }
}
