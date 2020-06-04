package com.mall.sls.common.unit;

public class BindWxManager {

    public static final String BINDWX = "BindWx";

    public static String getBindWx(){

        return SPManager.getInstance().getData(BINDWX);
    }

    public static void saveBindWx(String bindWx){
        SPManager.getInstance().putData(BINDWX,bindWx);
    }
}
