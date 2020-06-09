package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class JoinPrizeRequest {
    //抽奖id
    @SerializedName("prizeId")
    private String prizeId;
    //数量
    @SerializedName("number")
    private String number;
    //支付方式 0微信 1支付宝
    @SerializedName("payType")
    private String payType ;

    public JoinPrizeRequest(String prizeId, String number, String payType) {
        this.prizeId = prizeId;
        this.number = number;
        this.payType = payType;
    }
}
