package com.mall.sls.common.unit;

public class BaseUrlManager {

    public static final String BASE_URL = "BaseUrl";

    public static String getBaseUrl(){

        return SPManager.getInstance().getData(BASE_URL);
    }

    public static void saveBaseUrl(String baseUrl){
        SPManager.getInstance().putData(BASE_URL,baseUrl);
    }
}
