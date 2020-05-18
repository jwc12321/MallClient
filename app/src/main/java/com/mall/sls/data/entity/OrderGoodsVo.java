package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class OrderGoodsVo {
    //商品名字
    @SerializedName("goodsName")
    private String goodsName;
    //数量
    @SerializedName("number")
    private String number;
    //图片
    @SerializedName("picUrl")
    private String picUrl;
    //价格
    @SerializedName("price")
    private String price;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}
