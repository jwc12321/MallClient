package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class JoinPrizeInfo {
    //0:免费 1：需要付钱
    @SerializedName("free")
    private String free;
    //支付宝
    @SerializedName("aliPaySign")
    private String aliPaySign;
    //微信
    @SerializedName("wxPaySign")
    private WXPaySignResponse wxPaySignResponse;

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getAliPaySign() {
        return aliPaySign;
    }

    public void setAliPaySign(String aliPaySign) {
        this.aliPaySign = aliPaySign;
    }

    public WXPaySignResponse getWxPaySignResponse() {
        return wxPaySignResponse;
    }

    public void setWxPaySignResponse(WXPaySignResponse wxPaySignResponse) {
        this.wxPaySignResponse = wxPaySignResponse;
    }
}
