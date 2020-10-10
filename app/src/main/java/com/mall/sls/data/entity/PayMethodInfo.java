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
    //随机立减文案
    @SerializedName("description")
    private String description;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
