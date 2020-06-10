package com.mall.sls.common.unit;

public class SpikeManager {

    public static final String SPIKE = "Spike";

    public static String getSpike(){

        return SPManager.getInstance().getData(SPIKE);
    }

    public static void saveSpike(String token){
        SPManager.getInstance().putData(SPIKE,token);
    }
}
