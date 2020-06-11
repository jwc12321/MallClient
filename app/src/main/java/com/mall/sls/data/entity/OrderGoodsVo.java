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
    //sku
    @SerializedName("specifications")
    private String specifications;
    //产品秒速
    @SerializedName("brief")
    private String brief;
    //商品id
    @SerializedName("goodsId")
    private String goodsId;
    //skuId
    @SerializedName("productId")
    private String productId;
    //是否上架 0-已下架 1-未下架
    @SerializedName("isOnSale")
    private String isOnSale;


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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(String isOnSale) {
        this.isOnSale = isOnSale;
    }
}
