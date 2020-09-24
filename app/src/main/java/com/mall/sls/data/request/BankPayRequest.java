package com.mall.sls.data.request;

import com.mall.sls.data.entity.BaoFuPayInfo;
import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class BankPayRequest {
    @SerializedName("bindId")
    private String bindId;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("smsCode")
    private String smsCode;
    @SerializedName("paymentLog")
    private BaoFuPayInfo baoFuPayInfo;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public BaoFuPayInfo getBaoFuPayInfo() {
        return baoFuPayInfo;
    }

    public void setBaoFuPayInfo(BaoFuPayInfo baoFuPayInfo) {
        this.baoFuPayInfo = baoFuPayInfo;
    }
}
