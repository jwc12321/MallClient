package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author jwc on 2020/5/13.
 * 描述：规格价格列表
 */
public class ProductListCallableInfo {
    //图片
    @SerializedName("url")
    private String url;
    //价格
    @SerializedName("price")
    private String price;
    //规格id
    @SerializedName("id")
    private String id;

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
}
