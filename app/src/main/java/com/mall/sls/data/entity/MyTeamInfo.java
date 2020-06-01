package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class MyTeamInfo {
    //时间
    @SerializedName("addTime")
    private String addTime;
    //1活动团0日常团
    @SerializedName("isActivity")
    private String isActivity;
    //团购状态 1拼团中 3拼团成功
    @SerializedName("status")
    private String status;
    //图片
    @SerializedName("picUrl")
    private String picUrl;
    //商品名
    @SerializedName("name")
    private String name;
    //价格
    @SerializedName("goodsPrice")
    private String goodsPrice;
    //数量
    @SerializedName("number")
    private String number;
    //是付款
    @SerializedName("actualPrice")
    private String actualPrice;
    //订单id
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("grouponId")
    private String grouponId;
    @SerializedName("goodsProductId")
    private String goodsProductId;
    //产品描述
    @SerializedName("brief")
    private String brief;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGrouponId() {
        return grouponId;
    }

    public void setGrouponId(String grouponId) {
        this.grouponId = grouponId;
    }

    public String getGoodsProductId() {
        return goodsProductId;
    }

    public void setGoodsProductId(String goodsProductId) {
        this.goodsProductId = goodsProductId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
