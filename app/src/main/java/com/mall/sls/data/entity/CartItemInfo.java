package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.mall.sls.cart.adapter.Literature;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartItemInfo implements Literature {
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
    //购物车中的商品id
    @SerializedName("id")
    private String id;
    private String inputNumber="1";
    private boolean ischeck=false;

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

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return Literature.TYPE_NORMAL;
    }

    public String getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(String inputNumber) {
        this.inputNumber = inputNumber;
    }
}
