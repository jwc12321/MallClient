package com.mall.sls.common.unit;

public class TimeManager {

    public static final String TIME = "Time";

    public static String getTime(){

        return SPManager.getInstance().getData(TIME);
    }

    public static void saveTime(String time){
        SPManager.getInstance().putData(TIME,time);
    }
}
