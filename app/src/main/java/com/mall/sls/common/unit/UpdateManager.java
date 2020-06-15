package com.mall.sls.common.unit;

public class UpdateManager {

    public static final String UPDATE = "Update";

    public static String getUpdate(){

        return SPManager.getInstance().getData(UPDATE);
    }

    public static void saveUpdate(String version){
        SPManager.getInstance().putData(UPDATE,version);
    }
}
