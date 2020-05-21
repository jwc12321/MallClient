package com.mall.sls.common.unit;

public class MobileManager {

    public static final String MOBILE = "Mobile";

    public static String getMobile(){

        return SPManager.getInstance().getData(MOBILE);
    }

    public static void saveMobile(String mobile){
        SPManager.getInstance().putData(MOBILE,mobile);
    }
}
