package com.mall.sls.data.entity;

public class OrderTimeInfo {
    private String timeType;
    private String time;

    public OrderTimeInfo(String timeType, String time) {
        this.timeType = timeType;
        this.time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
