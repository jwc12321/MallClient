package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class OneClickLoginRequest {
    //一键登录token
    @SerializedName("accessCode")
    private String accessCode;
    //登录手机设备id
    @SerializedName("deviceId")
    private String deviceId;
    //登录设备系统版本
    @SerializedName("deviceOsVersion")
    private String deviceOsVersion ;
    //登录设备平台
    @SerializedName("devicePlatform")
    private String devicePlatform;

    public OneClickLoginRequest(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform) {
        this.accessCode = accessCode;
        this.deviceId = deviceId;
        this.deviceOsVersion = deviceOsVersion;
        this.devicePlatform = devicePlatform;
    }
}
