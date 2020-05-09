package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductSpuInfo {
    //spu编号
    @SerializedName("id")
    private String id;
    //SPU 名字
    @SerializedName("name")
    private String name;
    //商品缩略图URL
    @SerializedName("picUrl")
    private String picUrl;
    //发货地
    @SerializedName("place")
    private String place;
    //价格
    @SerializedName("price")
    private String price;
    //认购币种代码
    @SerializedName("purchaseCcyCode")
    private String purchaseCcyCode;
    //认购币种名称
    @SerializedName("purchaseCcyName")
    private String purchaseCcyName ;
    //是否推荐 0-不推荐 1-推荐
    @SerializedName("recommended")
    private String recommended;
    //月销量
    @SerializedName("sales")
    private String sales;
    //排序值
    @SerializedName("sort")
    private String sort;
    //状态 10-在售中 20-未上架 30-已售罄
    @SerializedName("status")
    private String status;
    //详情benner
    @SerializedName("picUrlsList")
    private List<String> picUrls;
    //描述
    @SerializedName("description")
    private String description;
    //是否允许自提
    @SerializedName("isAllowedExtract")
    private boolean isAllowedExtract;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurchaseCcyCode() {
        return purchaseCcyCode;
    }

    public void setPurchaseCcyCode(String purchaseCcyCode) {
        this.purchaseCcyCode = purchaseCcyCode;
    }

    public String getPurchaseCcyName() {
        return purchaseCcyName;
    }

    public void setPurchaseCcyName(String purchaseCcyName) {
        this.purchaseCcyName = purchaseCcyName;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAllowedExtract() {
        return isAllowedExtract;
    }

    public void setAllowedExtract(boolean allowedExtract) {
        isAllowedExtract = allowedExtract;
    }
}
