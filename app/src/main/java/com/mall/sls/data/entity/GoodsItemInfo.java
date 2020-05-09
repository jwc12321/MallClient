package com.mall.sls.data.entity;

/**
 * @author jwc on 2020/5/9.
 * 描述：
 */
public class GoodsItemInfo {
    private String name;
    private String goodsDetail;
    private String currentPirce;
    private String orPirce;

    public GoodsItemInfo(String name, String goodsDetail, String currentPirce, String orPirce) {
        this.name = name;
        this.goodsDetail = goodsDetail;
        this.currentPirce = currentPirce;
        this.orPirce = orPirce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getCurrentPirce() {
        return currentPirce;
    }

    public void setCurrentPirce(String currentPirce) {
        this.currentPirce = currentPirce;
    }

    public String getOrPirce() {
        return orPirce;
    }

    public void setOrPirce(String orPirce) {
        this.orPirce = orPirce;
    }
}
