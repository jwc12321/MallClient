package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/21.
 * 描述：
 */
public class OrderPayRequest {
    //订单号
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("type")
    private String type;

    public OrderPayRequest(String orderId, String type) {
        this.orderId = orderId;
        this.type = type;
    }
}
