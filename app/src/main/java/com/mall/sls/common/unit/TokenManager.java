package com.mall.sls.common.unit;

public class TokenManager {

    public static final String TOKEN = "Token";

    public static String getToken(){

        return SPManager.getInstance().getData(TOKEN);
    }

    public static void saveToken(String token){
        SPManager.getInstance().putData(TOKEN,token);
    }
}
