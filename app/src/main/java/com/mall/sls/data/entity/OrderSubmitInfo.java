package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class OrderSubmitInfo {
    //拼团信息id
    @SerializedName("grouponLinkId")
    private String grouponLinkId;
    //订单id
    @SerializedName("orderId")
    private String orderId ;
    @SerializedName("pay")
    private Boolean isPay;

    public String getGrouponLinkId() {
        return grouponLinkId;
    }

    public void setGrouponLinkId(String grouponLinkId) {
        this.grouponLinkId = grouponLinkId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }
}
