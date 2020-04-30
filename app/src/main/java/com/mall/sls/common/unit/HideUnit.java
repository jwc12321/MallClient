package com.mall.sls.common.unit;

import android.text.TextUtils;

public class HideUnit {
    public static String hideFirstName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 2) {
                return "**" + name.charAt(name.length() - 1);
            } else {
                return "*" + name.charAt(name.length() - 1);
            }
        }
        return "";
    }

    //张**
    public static String hideLastName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 2) {
                return name.charAt(0) + "**";
            } else {
                return name.charAt(0) + "*";
            }
        }
        return "";
    }

    //隐藏身份证  3306************7676
    public static String hideIdCard(String idCard) {
        if (!TextUtils.isEmpty(idCard) && idCard.length() > 8) {
            return idCard.substring(0, 4) + "**********" + idCard.substring(idCard.length()-4,idCard.length());
        }
        return idCard;
    }

    public static String hideName(String name,int number){
        if(!TextUtils.isEmpty(name)){
           if(name.length()>number){
               return name.substring(0,number)+"...";
           }else {
               return name;
           }
        }
        return "";
    }
}
