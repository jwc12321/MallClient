package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class ConfirmBindBankRequest {
    @SerializedName("id")
    private String id;
    //验证码
    @SerializedName("smsCode")
    private String smsCode;

    public ConfirmBindBankRequest(String id, String smsCode) {
        this.id = id;
        this.smsCode = smsCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
