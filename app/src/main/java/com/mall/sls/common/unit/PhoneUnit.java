package com.mall.sls.common.unit;

import android.text.TextUtils;

/**
 * @author jwc on 2020/7/23.
 * 描述：
 */
public class PhoneUnit {
    public static Boolean isPhone(String phoneNo){
        if(!TextUtils.isEmpty(phoneNo)&&phoneNo.length()==11){
            return true;
        }
        return false;
    }

    public static String hidePhone(String phoneNo){
        if(!TextUtils.isEmpty(phoneNo)&&phoneNo.length()==11){
            return phoneNo.substring(0,3)+"****"+phoneNo.substring(7,11);
        }
        return "";
    }
}
