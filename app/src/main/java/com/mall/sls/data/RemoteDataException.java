package com.mall.sls.data;


import android.content.Context;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RemoteDataException extends Exception {

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
