package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/30.
 * 描述：
 */
public class OrderIdRequest {
    @SerializedName("orderId")
    private String orderId;

    public OrderIdRequest(String orderId) {
        this.orderId = orderId;
    }
}
