package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.PipedReader;
import java.io.Serializable;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class CheckedGoods implements Serializable {
    //数量
    @SerializedName("number")
    private String number;
    //名字
    @SerializedName("goodsName")
    private String goodsName;
    //图片url
    @SerializedName("picUrl")
    private String picUrl;
    //价格
    @SerializedName("price")
    private String price;
    //是否团购
    @SerializedName("isGroup")
    private boolean isGroup;
    @SerializedName("preferentialPrice")
    private String preferentialPrice;
    @SerializedName("goodsId")
    private String goodsId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
