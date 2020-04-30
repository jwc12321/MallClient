package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class MobileRequest {
    //手机号
    @SerializedName("mobile")
    private String mobile;

    public MobileRequest(String mobile) {
        this.mobile = mobile;
    }
}
