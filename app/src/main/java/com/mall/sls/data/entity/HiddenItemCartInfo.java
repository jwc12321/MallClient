package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class HiddenItemCartInfo {
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
    @SerializedName("goodsId")
    private String goodsId;
    //skuID
    @SerializedName("productId")
    private String productId;
    //sku名称
    @SerializedName("specifications")
    private String specifications;
    //是否可以购买（下单商品列表 显示是否可以购买 false-显示灰色）
    @SerializedName("canBuy")
    private Boolean canBuy;

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Boolean getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(Boolean canBuy) {
        this.canBuy = canBuy;
    }
}
