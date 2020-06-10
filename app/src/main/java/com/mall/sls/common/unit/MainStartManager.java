package com.mall.sls.common.unit;

public class MainStartManager {

    public static final String MAIN_START = "MainStart";

    public static String getMainStart(){

        return SPManager.getInstance().getData(MAIN_START);
    }

    public static void saveMainStart(String token){
        SPManager.getInstance().putData(MAIN_START,token);
    }
}
