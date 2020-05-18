package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class GoodsOrderInfo {
    //订单id
    @SerializedName("id")
    private String id;
    //订单创建时间
    @SerializedName("addTime")
    private String addTime;
    //订单状态
    @SerializedName("orderStatus")
    private String orderStatus;
    //商品列表
    @SerializedName("orderGoodsVoList")
    private List<OrderGoodsVo> orderGoodsVos;
    //实付款
    @SerializedName("actualPrice")
    private String actualPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderGoodsVo> getOrderGoodsVos() {
        return orderGoodsVos;
    }

    public void setOrderGoodsVos(List<OrderGoodsVo> orderGoodsVos) {
        this.orderGoodsVos = orderGoodsVos;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }
}
