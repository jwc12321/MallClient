package com.mall.sls.common.unit;

public class LocalCityManager {

    public static final String LOCAL_CITY = "LocalCity";

    public static String getLocalCity(){

        return SPManager.getInstance().getData(LOCAL_CITY);
    }

    public static void saveLocalCity(String city){
        SPManager.getInstance().putData(LOCAL_CITY,city);
    }
}
