package com.mall.sls.common.unit;

import android.text.TextUtils;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FlashCartManager {

    public static final String FLASH_CART = "FlashCart";

    public static String getFlashCart(){

        return SPManager.getInstance().getData(FLASH_CART);
    }

    public static void saveFlashCart(String token){
        SPManager.getInstance().putData(FLASH_CART,token);
    }

    public static boolean isFlashCartValid(){
        return !TextUtils.isEmpty(getFlashCart());
    }

    public static void clearFlashCart(){
        saveFlashCart("");
    }
}
