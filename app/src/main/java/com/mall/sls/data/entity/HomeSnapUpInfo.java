package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/9/21.
 * 描述：
 */
public class HomeSnapUpInfo {
     //商品id
    @SerializedName("goodsId")
    private String goodsId;
    //商品名字
    @SerializedName("name")
    private String name;
    //团购价
    @SerializedName("retailPrice")
    private String retailPrice;
    //原价
    @SerializedName("counterPrice")
    private String counterPrice;
    //商品类型 1普通商品 2普通团商品 3活动团商品 4抽奖商品
    @SerializedName("goodsType")
    private String goodsType;
    //图片
    @SerializedName("picUrl")
    private String picUrl;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(String counterPrice) {
        this.counterPrice = counterPrice;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
