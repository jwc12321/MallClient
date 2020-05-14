package com.mall.sls.common.unit;

import android.text.TextUtils;

public class TimeUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long week= 7* day;//一星期
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @return
     */
    public static String getTimeFormatText(String now,String time) {
        if (TextUtils.equals("-1",now)||TextUtils.equals("-1",time)) {
            return null;
        }
        long diff = Long.parseLong(now) - Long.parseLong(time);
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if(diff>week){
            r=(diff / week);
            return r+"个星期前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";

    }
}
