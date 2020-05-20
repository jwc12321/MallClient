package com.mall.sls.common.unit;

public class ClientIdManager {

    public static final String CLIENT_ID = "ClientId";

    public static String getClientId(){

        return SPManager.getInstance().getData(CLIENT_ID);
    }

    public static void saveClientId(String clientId){
        SPManager.getInstance().putData(CLIENT_ID,clientId);
    }
}
