package com.mall.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/25.
 * 描述：
 */
public class OrderAddCartRequest {
    //订单id
    @SerializedName("orderId")
    private String orderId ;
    //是否强制添加
    @SerializedName("forceAdd")
    private Boolean forceAdd;

    public OrderAddCartRequest(String orderId, Boolean forceAdd) {
        this.orderId = orderId;
        this.forceAdd = forceAdd;
    }
}
