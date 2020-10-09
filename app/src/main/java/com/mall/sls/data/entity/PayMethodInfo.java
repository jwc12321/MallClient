package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/10/9.
 * 描述：
 */
public class PayMethodInfo {
    //可用值:WxPay,Alipay,MiniPay,BaoFooPay
    @SerializedName("paymentMethod")
    private String paymentMethod;
    //是否可随机立减
    @SerializedName("subPercent")
    private Boolean subPercent;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getSubPercent() {
        return subPercent;
    }

    public void setSubPercent(Boolean subPercent) {
        this.subPercent = subPercent;
    }
}
