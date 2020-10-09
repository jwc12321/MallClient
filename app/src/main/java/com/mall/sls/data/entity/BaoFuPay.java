package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class BaoFuPay {
    @SerializedName("userPay")
    private UserPayInfo userPayInfo;

    public UserPayInfo getUserPayInfo() {
        return userPayInfo;
    }

    public void setUserPayInfo(UserPayInfo userPayInfo) {
        this.userPayInfo = userPayInfo;
    }
}
