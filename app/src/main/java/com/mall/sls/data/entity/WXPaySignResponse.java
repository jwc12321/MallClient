package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/5/4.
 */

public class WXPaySignResponse {
    @SerializedName("appId")
    private String appid;
    @SerializedName("partnerId")
    private String partnerid;
    @SerializedName("prepayId")
    private String prepayid;
    @SerializedName("packageValue")
    private String packageValue;
    @SerializedName("nonceStr")
    private String noncestr;
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

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
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
