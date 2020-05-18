package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class OrderList {
    @SerializedName("list")
    private List<GoodsOrderInfo> goodsOrderInfos;

    public List<GoodsOrderInfo> getGoodsOrderInfos() {
        return goodsOrderInfos;
    }

    public void setGoodsOrderInfos(List<GoodsOrderInfo> goodsOrderInfos) {
        this.goodsOrderInfos = goodsOrderInfos;
    }
}
