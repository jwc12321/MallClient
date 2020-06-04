package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/9.
 * 描述：
 */
public class GoodsItemInfo implements Serializable {
    //产品秒速
    @SerializedName("brief")
    private String brief;
    //商品id
    @SerializedName("goodsId")
    private String goodsId;
    //团购规则id
    @SerializedName("grouponRulesId")
    private String grouponRulesId;
    //名字
    @SerializedName("name")
    private String name;
    //图片
    @SerializedName("picUrl")
    private String picUrl;
    //原价
    @SerializedName("counterPrice")
    private String counterPrice;
    //团购价
    @SerializedName("retailPrice")
    private String retailPrice;
    //团类型0日常团 1活动团
    @SerializedName("groupType")
    private String groupType;
    //订阅状态 0:未提醒 1：已预约
    @SerializedName("subscriptionStatus")
    private String subscriptionStatus;
    //团购开始时间
    @SerializedName("groupStartTime")
    private String startTime;
    //标签
    @SerializedName("keywords")
    private String keywords;
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGrouponRulesId() {
        return grouponRulesId;
    }

    public void setGrouponRulesId(String grouponRulesId) {
        this.grouponRulesId = grouponRulesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(String counterPrice) {
        this.counterPrice = counterPrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
