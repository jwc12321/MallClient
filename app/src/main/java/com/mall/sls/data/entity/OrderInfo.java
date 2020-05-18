package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class OrderInfo {
    @SerializedName("orderInfo")
    private GoodsOrderDetails goodsOrderDetails;

    public GoodsOrderDetails getGoodsOrderDetails() {
        return goodsOrderDetails;
    }

    public void setGoodsOrderDetails(GoodsOrderDetails goodsOrderDetails) {
        this.goodsOrderDetails = goodsOrderDetails;
    }
}
