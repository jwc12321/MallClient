package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/30.
 * 描述：
 */
public class PayRequest {
    @SerializedName("orderId")
    private String orderId;
    //支付类型 CERTIFY 认证支付 ，SUPER 超级会员支付， ORDER 订单支付
    @SerializedName("orderType")
    private String orderType;
    //WxPay Alipay BaoFooPay
    @SerializedName("paymentMethod")
    private String paymentMethod;

    public PayRequest(String orderId, String orderType, String paymentMethod) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
    }
}
