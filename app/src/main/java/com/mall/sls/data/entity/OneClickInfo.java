package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

public class OneClickInfo {
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //登录token
    @SerializedName("token")
    private String token;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
