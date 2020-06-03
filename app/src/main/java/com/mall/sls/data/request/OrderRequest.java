package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/3.
 * 描述：
 */
public class OrderRequest {
    @SerializedName("orderId")
    private String orderId;

    public OrderRequest(String orderId) {
        this.orderId = orderId;
    }
}
