package com.mall.sls.common.unit;

import android.text.TextUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberFormatUnit {
    //自动取消小数点后面的0
    public static String numberFormat(String number) {
        if (!TextUtils.isEmpty(number)) {
            if (number.indexOf(".") > 0) {
                number = number.replaceAll("0+?$", "");//去掉多余的0
                number = number.replaceAll("[.]$", "");//如最后一位是.则去掉
            }
            return number;
        } else {
            return "";
        }
    }


    //小数点后不足6位自动补气6位，6-10，就显示6-10位，超过10就只显示10位
    public static String minePoolNumber(String number) {
        if (!TextUtils.isEmpty(number)) {
            if (number.indexOf(".") > 0) {
                String[] numbers = number.split("\\.");
                if (numbers[1].length() < 6) {
                    return sixDecimalFormat(number);
                } else if (numbers[1].length() >= 6 && numbers[1].length() <= 10) {
                    return number;
                } else {
                    return numbers[0] + "." + numbers[1].substring(0, 10);
                }
            }else {
                return sixDecimalFormat(number);
            }
        }else {
            return "";
        }
    }

    //自动补齐6个0
    public static String sixDecimalFormat(String number) {
        if (!TextUtils.isEmpty(number)) {
            DecimalFormat df = new DecimalFormat("0.000000");
            return df.format(Double.parseDouble(number));
        } else {
            return "";
        }
    }

    //自动补齐2个0
    public static String twoDecimalFormat(String number) {
        if (!TextUtils.isEmpty(number)) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(Double.parseDouble(number));
        } else {
            return "";
        }
    }

    //判断是否大于10000
    public static String tenThousand(String number){
        if(!TextUtils.isEmpty(number)){
            BigDecimal numberBg=new BigDecimal(number);
            BigDecimal tenThousandBd=new BigDecimal("10000");
            if(Integer.parseInt(number)>10000){
                return numberBg.divide(tenThousandBd).setScale(1, RoundingMode.HALF_UP).toString()+"万";
            }else {
                return number;
            }
        }else {
            return "0";
        }
    }

}
