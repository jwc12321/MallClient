package com.mall.sls.data;


import android.content.Context;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RemoteDataException extends Exception {


    public static final String TOKEN_OVER_TWO="10002";//token失效
    public static final String TOKEN_OVER_ONE="10001";//token失效
    public static final String CODE_SEVEN_ZERO_ONE="710";//库存不足

    private String mRetCode;

    public RemoteDataException(String retCode) {
        super();
        mRetCode = retCode;
    }

    public RemoteDataException(String retCode, String message) {
        super(message);
        mRetCode = retCode;
    }

    public String getRetCode() {
        return mRetCode;
    }

    public String getMessage(Context context) {

        if (super.getMessage() != null) {
            return super.getMessage();
        }
        return "";
    }
}
