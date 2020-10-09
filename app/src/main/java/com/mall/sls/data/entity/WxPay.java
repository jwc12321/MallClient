package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/7/14.
 * 描述：
 */
public class WxPay {
    @SerializedName("param")
    private WXPaySignResponse wxPayInfo;
    @SerializedName("userPay")
    private UserPayInfo userPayInfo;


    public WXPaySignResponse getWxPayInfo() {
        return wxPayInfo;
    }

    public void setWxPayInfo(WXPaySignResponse wxPayInfo) {
        this.wxPayInfo = wxPayInfo;
    }

    public UserPayInfo getUserPayInfo() {
        return userPayInfo;
    }

    public void setUserPayInfo(UserPayInfo userPayInfo) {
        this.userPayInfo = userPayInfo;
    }
}
