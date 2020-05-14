package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jwc on 2020/5/13.
 * 描述：规格价格列表
 */
public class ProductListCallableInfo implements Serializable {
    //图片
    @SerializedName("url")
    private String url;
    //价格
    @SerializedName("price")
    private String price;
    //规格id
    @SerializedName("id")
    private String id;
    //优惠价
    @SerializedName("preferentialPrice")
    private String preferentialPrice;
    //sku
    @SerializedName("specifications")
    private String specifications;
    //库存
    @SerializedName("number")
    private String number;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
