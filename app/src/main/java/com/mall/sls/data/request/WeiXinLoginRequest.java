package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class WeiXinLoginRequest {
    //登录手机设备id
    @SerializedName("deviceId")
    private String deviceId;
    //登录设备系统版本
    @SerializedName("deviceOsVersion")
    private String deviceOsVersion ;
    //登录设备平台
    @SerializedName("devicePlatform")
    private String devicePlatform;
    //微信code
    @SerializedName("wxCode")
    private String wxCode;
    //设备名
    @SerializedName("deviceName")
    private String deviceName;

    public WeiXinLoginRequest(String deviceId, String deviceOsVersion, String devicePlatform, String wxCode,String deviceName) {
        this.deviceId = deviceId;
        this.deviceOsVersion = deviceOsVersion;
        this.devicePlatform = devicePlatform;
        this.wxCode = wxCode;
        this.deviceName=deviceName;
    }
}
