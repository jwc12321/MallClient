package com.mall.sls.common.unit;

public class AreaCodeManager {

    public static final String AREACODE = "AreaCode";

    public static String getAreaCode(){

        return SPManager.getInstance().getData(AREACODE);
    }

    public static void saveAreaCode(String areaCode){
        SPManager.getInstance().putData(AREACODE,areaCode);
    }
}
