package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class GoodsOrderInfo {
    //订单总金额
    @SerializedName("amount")
    private String amount;
    //订单状态
    @SerializedName("status")
    private String status;
    //订单号
    @SerializedName("orderNo")
    private String orderNo;
    //订单编号
    @SerializedName("id")
    private String id;
    //数量
    @SerializedName("quantity")
    private String quantity;
    //名字
    @SerializedName("name")
    private String name;
    //图片url
    @SerializedName("picUrl")
    private String picUrl;



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GoodsOrderInfo(String amount, String status, String quantity, String name) {
        this.amount = amount;
        this.status = status;
        this.quantity = quantity;
        this.name = name;
    }
}
