package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class GoodsBaseVo {
    //商品宣传图片列表
    @SerializedName("gallery")
    private List<String> gallerys;
    //商品名
    @SerializedName("name")
    private String name;
    //产品秒速
    @SerializedName("brief")
    private String brief;
    //原价
    @SerializedName("counterPrice")
    private String counterPrice;
    //团购价
    @SerializedName("retailPrice")
    private String retailPrice;
    //总销量
    @SerializedName("salesQuantity")
    private String salesQuantity;
    //详情
    @SerializedName("detail")
    private String detail;
    //图片url
    @SerializedName("picUrl")
    private String picUrl;
    //发货方式 1同城配送 2快递配送
    @SerializedName("courierType")
    private String courierType;

    public List<String> getGallerys() {
        return gallerys;
    }

    public void setGallerys(List<String> gallerys) {
        this.gallerys = gallerys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(String salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }
}
