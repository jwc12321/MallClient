package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/7/14.
 * 描述：
 */
public class AliPay {
    @SerializedName("param")
    private String aliPayInfo;
    @SerializedName("userPay")
    private UserPayInfo userPayInfo;

    public String getAliPayInfo() {
        return aliPayInfo;
    }

    public void setAliPayInfo(String aliPayInfo) {
        this.aliPayInfo = aliPayInfo;
    }

    public UserPayInfo getUserPayInfo() {
        return userPayInfo;
    }

    public void setUserPayInfo(UserPayInfo userPayInfo) {
        this.userPayInfo = userPayInfo;
    }
}
