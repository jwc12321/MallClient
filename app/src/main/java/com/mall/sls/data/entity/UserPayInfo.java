package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/10/9.
 * 描述：
 */
public class UserPayInfo implements Serializable {
    //原价
    @SerializedName("orderPrice")
    private String orderPrice;
    //实付金额
    @SerializedName("actualPrice")
    private String actualPrice;
    //优惠金额
    @SerializedName("subAmount")
    private String subAmount;
    //宝付专用
    @SerializedName("id")
    private String id;
    //随机立减提示
    @SerializedName("subDes")
    private String subDes;
    //订单编号
    @SerializedName("orderSn")
    private String orderSn;

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getSubDes() {
        return subDes;
    }

    public void setSubDes(String subDes) {
        this.subDes = subDes;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
