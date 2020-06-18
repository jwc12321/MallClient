package com.mall.sls.common.unit;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by JWC on 2017/4/27.
 */

public class FormatUtil {
    private static String[] weekDays = "周日 周一 周二 周三 周四 周五 周六".split(" ");

    private static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    private static final int HOUR = 60 * 60;

    private static final int MINUTE = 60;

    private FormatUtil() {
    }


    public static String GTMToLocal(String GTMDate) {
        int tIndex = GTMDate.indexOf("T");
        String dateTemp = GTMDate.substring(0, tIndex);
        String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length() - 6);
        String convertString = dateTemp + " " + timeTemp;


        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        Date result_date;
        long result_time = 0;


        if (null == GTMDate) {
            return GTMDate;
        } else {
            try {
                format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = format.parse(convertString);
                result_time = result_date.getTime();
                format.setTimeZone(TimeZone.getDefault());
                return format.format(result_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return GTMDate;
    }

    public static String GTMToLocalNoAdd(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            int tIndex = GTMDate.indexOf("T");
            String dateTemp = GTMDate.substring(0, tIndex);
            String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length());
            String convertString = dateTemp + " " + timeTemp;


            SimpleDateFormat format;
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date result_date;
            long result_time = 0;


            if (null == GTMDate) {
                return GTMDate;
            } else {
                try {
                    format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                    result_date = format.parse(convertString);
                    result_time = result_date.getTime();
                    format.setTimeZone(TimeZone.getDefault());
                    return format.format(result_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return GTMDate;
        }
        return "";
    }

    /**
     * 后台是java的话，虽然后台返回时间戳，但是绘自己转换，所以要自己先转会时间戳
     *
     * @param dateString
     * @return
     */
    public static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String time(String dateString) {
        if (!TextUtils.isEmpty(dateString)) {
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);//输入的被转化的时间格式
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//需要转化成的时间格式
            Date date1 = new Date();
            try {
                date1 = dff.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String str1 = df1.format(date1);
            return str1;
        }
        return "";
    }


    /**
     * 加密号码的倒数第8位（包括）之倒数第4位（不包括）
     *
     * @param phoneNumber 原号码
     * @return 加密后的号码
     */
    public static String encryptPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        int length = phoneNumber.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (i >= length - 8 && i < length - 4) {
                sb.append("*");
            } else {
                sb.append(phoneNumber.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 加密号码的倒数第16位（包括）之倒数第4位（不包括）
     *
     * @param phoneNumber 原号码
     * @return 加密后的号码
     */
    public static String encryptNewPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        int length = phoneNumber.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (i >= length - 12 && i < length - 4) {
                sb.append("*");
            } else {
                sb.append(phoneNumber.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 格式化时间 03:12:15
     *
     * @param timeString 秒
     * @return 格式化后的时间
     */
    public static String formatTime(String timeString) {
        Long time = Long.parseLong(timeString);
        int hours = (int) (time / HOUR);
        int minute = (int) ((time - hours * HOUR) / MINUTE);
        int second = (int) ((time - hours * HOUR) % MINUTE);
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minute, second);
    }

    /**
     * 获取月份
     *
     * @param timestamp 秒时
     * @return 第几个月 JANUARY 对应 1，FEBRUARY 对应 2
     */
    public static int getMonth(String timestamp) {
        long time = Long.parseLong(timestamp) * 1000;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取年份
     *
     * @param timestamp 秒时
     * @return 年份
     */
    public static int getYear(String timestamp) {
        long time = Long.parseLong(timestamp) * 1000;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int thisYear() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(Calendar.YEAR);
    }

    public static String timeSecond() {
        long millisecond = System.currentTimeMillis();
        long second = millisecond / 1000;
        return String.valueOf(second);
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public static int thisMonth() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(Calendar.MONTH);
    }


    //android格林威治时间转成本地时间
    public static String formatDateByLine(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "yyyy/MM/dd  HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    //android格林威治时间转成本地时间
    public static String formatTeamDateByLine(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "yyyy.MM.dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    //android格林威治时间转成本地时间
    public static String formatTeamDateByHmLine(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "yyyy.MM.dd HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }


    //android格林威治时间转成本地时间
    public static String formatDateByLineHms(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    public static String formatSDateByLineHms(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "yyyy/MM/dd HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    //android格林威治时间转成本地时间
    public static String formatDateByLineHm(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }


    //android格林威治时间转成本地时间
    public static String formatDateYMD(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            int tIndex = GTMDate.indexOf("T");
            String dateTemp = GTMDate.substring(0, tIndex);
            String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length() - 6);
            String convertString = dateTemp + " " + timeTemp;
            SimpleDateFormat format;
            format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date result_date;
            long result_time = 0;
            if (null == GTMDate) {
                return GTMDate;
            } else {
                try {
                    format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                    result_date = format.parse(convertString);
                    result_time = result_date.getTime();
                    format.setTimeZone(TimeZone.getDefault());
                    return format.format(result_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return GTMDate;
        } else {
            return "";
        }
    }

    //android格林威治时间转成本地时间
    public static String formatDateHM(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            String time = formatDateByLine(GTMDate);
            String[] parts = time.split(" ");
            return parts[1];
        } else {
            return "";
        }
    }


    public static String formatDateBySlash(String dateString) {
        String timestamp = getStringToDate(dateString) + "";
        if (!TextUtils.isEmpty(timestamp)) {
            long time = Long.parseLong(timestamp);
            String pattern = "yyyy/MM/dd  HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    public static String formatMonthByLine(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            long time = Long.parseLong(timestamp);
            String pattern = "MM-dd HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }


    public static String formatYearMonthByLine(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            long time = Long.parseLong(timestamp);
            String pattern = "yyyy-MM-dd HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    public static String formatYMByLine() {
        long time = System.currentTimeMillis();
        String pattern = "yyyy-MM";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    public static String formatMByLine() {
        long time = System.currentTimeMillis();
        String pattern = "MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    public static String formatYMDByLine() {
        long time = System.currentTimeMillis();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    public static String formatDayTime(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            long time = Long.parseLong(timestamp);
            String pattern = "HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time * 1000));
        } else {
            return "";
        }
    }

    public static String formatDateYear(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            long time = Long.parseLong(timestamp);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time * 1000));
        } else {
            return "";
        }
    }

    /**
     * 格式化日期时间
     *
     * @param timestamp 秒时
     * @return 格式化后的时间
     */
    public static String formatDate(String timestamp) {
        return formatDate(timestamp, "yyyy年MM月dd日");
    }


    public static String formatDateTime(String timestamp) {
        long time = Long.parseLong(timestamp);
        String pattern = "yyyy-MM";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time * 1000));
    }

    public static String formatDateMonth(long time) {
        String pattern = "yyyy年MM月";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }


    public static String formatDates(String timestamp, int thisYear, int thisMonth) {
        if (timestamp != null) {
            String time = formatDateTime(timestamp);
            String times[] = time.split("-");
            String str_month = times[1];
            if (str_month.substring(0, 1).equals("0")) {
                str_month = str_month.substring(1, 2);
            }
            if (Integer.parseInt(times[0]) == thisYear && Integer.parseInt(str_month) == thisMonth) {
                return "本月";
            } else {
                return time + "月";
            }

        }
        return "";
    }

    /**
     * 根据一个特殊的规则来格式化
     */
    public static String formatNewDate(String timestamp, String dateTemplate) {
        long time = Long.parseLong(timestamp) * 1000;
        Date date = new Date(time);
        String format;
        if (time >= today() && time < today() + ONE_DAY) {
            format = "今天";
        } else if (time >= today() + ONE_DAY && time < today() + ONE_DAY * 2) {
            format = "明天 ";
        } else if (time >= today() + ONE_DAY * 2 && time < today() + ONE_DAY * 3) {
            format = "后天 ";
        } else if (time >= today() - ONE_DAY && time < today()) {
            format = "昨天 ";
        } else {
            format = dateTemplate + " ";
        }
        format += " HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据一个特殊的规则来格式化
     */
    public static String formatDate(String timestamp, String dateTemplate) {
        if(TextUtils.isEmpty(timestamp)||TextUtils.equals("-1",timestamp)){
            return "";
        }
        long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        String format;
        boolean showDetailTime = true;

        if (time >= today() && time < today() + ONE_DAY) {
            format = "";
        } else if (time >= today() + ONE_DAY && time < today() + ONE_DAY * 2) {
            format = "明天 ";
        } else if (time >= today() + ONE_DAY * 2 && time < today() + ONE_DAY * 3) {
            format = "后天 ";
        } else if (time >= today() - ONE_DAY && time < today()) {
            format = "昨天 ";
        } else if (time >= today() - ONE_DAY * 2 && time < today() - ONE_DAY) {
            format = "前天 ";
        } else if (time >= thisWeek() && time < thisWeek() + ONE_DAY * 7) {
            format = weekDays[dayOfWeek(time) - 1] + " ";
        } else {
            format = dateTemplate + " ";
            showDetailTime = false;
        }
        if (showDetailTime) {
            format += "HH:mm";
//            if (afterNoon(time)) {
//                format += " 下午hh:mm";
//            } else {
//                format += " 上午hh:mm";
//            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取今天凌晨的毫秒时
     *
     * @return 今天凌晨的毫秒时
     */
    public static long today() {
//        long current = System.currentTimeMillis();
//        return current - current % ONE_DAY;
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - 8* 60 * 60 *1000);
    }

    /**
     * 一星期中的第几天
     *
     * @param time 毫秒时
     * @return 1-7 ：周日-周六
     */
    public static int dayOfWeek(long time) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断是否为一天中的下午
     *
     * @param time 时间
     * @return true, 如果该时刻是一天中的下午
     */
    public static boolean afterNoon(long time) {
        return (time + TimeZone.getDefault().getRawOffset()) % ONE_DAY > ONE_DAY / 2;
    }


    /**
     * 获取本周日零时的毫秒时
     *
     * @return 本周日零时的毫秒时
     */
    public static long thisWeek() {
        return today() - (dayOfWeek(System.currentTimeMillis()) - 1) * ONE_DAY;
    }

    /**
     * 拼接服务内容
     *
     * @param service 服务列表
     * @param split   分隔符
     * @return 拼接后的字符串
     */
    public static String spliceService(List<String> service, String split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < service.size(); i++) {
            String strService = service.get(i);
            sb.append(strService);
            if (i < service.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

    public static int nowYear() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        return nowYear;
    }

    public static int nowMonth() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        return nowMonth;
    }

    public static int nowDay() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        return nowDay;
    }


    public static String nowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    //android格林威治时间转成本地时间
    public static String formatDateByHm(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    //android格林威治时间转成本地时间
    public static String formatDateBymdHm(String GTMDate) {
        if (!TextUtils.isEmpty(GTMDate)) {
            long time = Long.parseLong(GTMDate);
            String pattern = "MM-dd HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        } else {
            return "";
        }
    }

    /*将字符串转为时间戳*/
    public static long dateToStamp(String time) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return date.getTime();
        }
    }

    public static String formatMSDateTime(String timestamp) {
        if(TextUtils.isEmpty(timestamp)){
            return "";
        }else {
            long time = Long.parseLong(timestamp);
            String pattern = "HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time));
        }
    }

    public static long day(long now, long endTime) {
        long surplus = (endTime - now) / (24 * 60 * 60) / 1000;
        return surplus;
    }
}
