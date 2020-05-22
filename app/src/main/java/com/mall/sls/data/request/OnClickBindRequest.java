package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class OnClickBindRequest {
    //登录手机设备id
    @SerializedName("deviceId")
    private String deviceId;
    //登录设备系统版本
    @SerializedName("deviceOsVersion")
    private String deviceOsVersion ;
    //登录设备平台
    @SerializedName("devicePlatform")
    private String devicePlatform;
    //一键登录code
    @SerializedName("accessCode")
    private String accessCode;
    //邀请码
    @SerializedName("invitationCode")
    private String invitationCode;
    //unionId
    @SerializedName("unionId")
    private String unionId;

    public OnClickBindRequest(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId) {
        this.deviceId = deviceId;
        this.deviceOsVersion = deviceOsVersion;
        this.devicePlatform = devicePlatform;
        this.accessCode = accessCode;
        this.invitationCode = invitationCode;
        this.unionId = unionId;
    }
}
