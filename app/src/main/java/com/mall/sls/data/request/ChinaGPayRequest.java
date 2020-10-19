package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/19.
 * 描述：
 */
public class ChinaGPayRequest {
    //支付id
    @SerializedName("payId")
    private String payId;
    //验证码
    @SerializedName("smsCode")
    private String smsCode;
    //爱农tn
    @SerializedName("tn")
    private String tn;

    public ChinaGPayRequest(String payId, String smsCode, String tn) {
        this.payId = payId;
        this.smsCode = smsCode;
        this.tn = tn;
    }
}
