package com.mall.sls.common.unit;

public class VerifyManager {

    public static final String VREIFY = "Verify";

    public static String getVerify(){

        return SPManager.getInstance().getData(VREIFY);
    }

    public static void saveVerify(String verify){
        SPManager.getInstance().putData(VREIFY,verify);
    }
}
