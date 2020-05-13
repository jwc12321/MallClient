package com.mall.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class GoodsDetailsInfo {
    //产品秒速
    @SerializedName("brief")
    private String brief;
    //商品id
    @SerializedName("goodsId")
    private String goodsId;
    //商品宣传图片列表
    @SerializedName("gallery")
    private List<String> gallerys;
    //商品名
    @SerializedName("name")
    private String name;
    //优惠金额
    @SerializedName("preferentialPrice")
    private String preferentialPrice;
    //原价
    @SerializedName("counterPrice")
    private String counterPrice;
    //单位
    @SerializedName("unit")
    private String unit;
    //规格列表
    @SerializedName("specificationList")
    private List<GoodsSpec> goodsSpecs;
    //规格价格列表
    @SerializedName("productListCallableList")
    private List<ProductListCallableInfo> productListCallableInfos;

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

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(String counterPrice) {
        this.counterPrice = counterPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<GoodsSpec> getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(List<GoodsSpec> goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public List<ProductListCallableInfo> getProductListCallableInfos() {
        return productListCallableInfos;
    }

    public void setProductListCallableInfos(List<ProductListCallableInfo> productListCallableInfos) {
        this.productListCallableInfos = productListCallableInfos;
    }
}
