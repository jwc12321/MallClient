package com.mall.sls.common.unit;

import java.util.regex.Pattern;

public class RegularUnit {
    /**
     * 名字
     */
    public static Boolean validateName(String name) {
        return name.matches("^([\\u4e00-\\u9fa5]{1,20})$");
    }

    //19和20以后你们可以换，我是不可能了
    public static Boolean vaildateIdCard(String idcard){
//        String pattern = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2][0-9])|3[0-1])((\\d{4})|\\d{3}X)$";
        String pattern="^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
        return Pattern.matches(pattern, idcard);
    }


}
