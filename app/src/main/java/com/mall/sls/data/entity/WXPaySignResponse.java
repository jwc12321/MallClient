package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/5/4.
 */

public class WXPaySignResponse {
    @SerializedName("appId")
    private String appid;
    @SerializedName("partnerId")
    private String partnerId;
    @SerializedName("prepayId")
    private String prepayId;
    @SerializedName("packageValue")
    private String packageValue;
    @SerializedName("nonceStr")
    private String nonceStr;
    @SerializedName("timeStamp")
    private String timestamp;
    @SerializedName("sign")
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
