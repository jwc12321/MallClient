package com.mall.sls.data;


import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class RemoteDataWrapper<T> {
    public static final String CODE_SUCCESS = "0";
    @Nullable
    @SerializedName("code")
    public String errorCode;
    @Nullable
    @SerializedName("msg")
    public String errorStr;
    @Nullable
    @SerializedName("success")
    public Boolean success;
    @Nullable
    @SerializedName("data")
    public T data;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorStr() {
        return errorStr;
    }

    /**
     * 判断请求数据是否成功
     */
    public boolean isSuccess() {
        return errorCode.equals(CODE_SUCCESS);
    }
}
