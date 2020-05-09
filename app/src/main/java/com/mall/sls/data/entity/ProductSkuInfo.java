package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSkuInfo implements Serializable {
    //商品规格
    @SerializedName("attrs")
    private String attrs;
    //商品sku编号
    @SerializedName("id")
    private String id;
    //商品缩略图URL
    @SerializedName("picUrl")
    private String picUrl;
    //价格
    @SerializedName("price")
    private String price;
    //库存数量
    @SerializedName("quantity")
    private String quantity;
    //状态 10-在售中 20-未上架 30-已售罄
    @SerializedName("status")
    private String status;

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
