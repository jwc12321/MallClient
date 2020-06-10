package com.mall.sls.common.unit;

public class PrivacyManager {

    public static final String PRIVACY = "Privacy";

    public static String getPrivacy(){

        return SPManager.getInstance().getData(PRIVACY);
    }

    public static void savePrivacy(String privacy){
        SPManager.getInstance().putData(PRIVACY,privacy);
    }
}
