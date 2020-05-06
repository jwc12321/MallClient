package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    //登录手机设备id
    @SerializedName("deviceId")
    private String deviceId;
    //登录设备系统版本
    @SerializedName("deviceOsVersion")
    private String deviceOsVersion ;
    //登录设备平台
    @SerializedName("devicePlatform")
    private String devicePlatform;
    //手机号
    @SerializedName("mobile")
    private String mobile;
    //短信验证码
    @SerializedName("code")
    private String code;

    public LoginRequest(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code) {
        this.deviceId = deviceId;
        this.deviceOsVersion = deviceOsVersion;
        this.devicePlatform = devicePlatform;
        this.mobile = mobile;
        this.code = code;
    }
}
